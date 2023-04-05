package com.service.organisation.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.service.organisation.entity.Organisation;
import com.service.organisation.exceptions.CustomExceptions;
import com.service.organisation.repositories.OrgRepository;
import com.service.organisation.util.Helper;

@Service
public class OrgServiceImpl implements OrgService{

	@Autowired private OrgRepository orgRepo;
	@Override
	public Organisation createOrg(Organisation organisation) {
		if(checkIfOrgAlreadyExist(organisation.getOrganisationEmail())==null) {
			try {
				organisation.setPassword(Helper.getEncryptedPassword(organisation.getPassword()));
				return orgRepo.save(organisation);
			} catch (Exception e) {
				throw new CustomExceptions(e.getMessage(),organisation);
			}
		}else
		throw new CustomExceptions("Organisation Already Exist with the email : ", organisation.getOrganisationEmail());
	}
	private Organisation checkIfOrgAlreadyExist(String orgEmail) {
		Optional<Organisation> orgByEmail = orgRepo.findByOrganisationEmail(orgEmail);
		if(orgByEmail.isPresent()) {
			return orgByEmail.get();
		}
		return null;
	}

}
