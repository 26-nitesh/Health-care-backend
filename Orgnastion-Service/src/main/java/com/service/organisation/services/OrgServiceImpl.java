package com.service.organisation.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.service.organisation.entity.Organisation;
import com.service.organisation.exceptions.CustomExceptions;
import com.service.organisation.exceptions.ResourceNotFoundException;
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
	@Override
	public Organisation getByEmail(String email) {
		Organisation byEmail = checkIfOrgAlreadyExist(email);
		   if(byEmail!=null) {
			   return byEmail;
		   }
			throw new ResourceNotFoundException("Orgaisation", "email", email);
		}
	@Override
	public List<Organisation> getByAgencyEmail(String email) {
		List<Organisation> Orgs = orgRepo.findByInsuranceAgencyEmail(email);
		if(!Orgs.isEmpty()) {
			return Orgs;
		}
		throw new CustomExceptions("Organisations Not Found with agency the email : ", email);
	}
	@Override
	public List<Organisation> deleteByAgencyEmail(String email) {
		List<Organisation> Orgs = getByAgencyEmail(email);
		if(!Orgs.isEmpty()) {
//		List<Integer> ids = Orgs.stream().map(o->o.getOrgId()).collect(Collectors.toList());
//		orgRepo.deleteAllById(ids);
			orgRepo.deleteAllByInsuranceAgencyEmail(email);
			return Orgs;
		}
		throw new CustomExceptions("Organisations Not Found with agency the email : ", email);
	}
	@Override
	public List<Integer> findPolicyIdsByEmail(String email) {
		Organisation byEmail = checkIfOrgAlreadyExist(email);
		   if(byEmail!=null) {
			   return byEmail.getPolicyIds();
		   }
			throw new ResourceNotFoundException("Orgaisation", "email", email);
		}
	@Override
	public Organisation deleteByEmail(String email) {
		Organisation byEmail = checkIfOrgAlreadyExist(email);
		   if(byEmail!=null) {
			   orgRepo.deleteById(byEmail.getOrgId());
			   return byEmail;
		   }
			throw new ResourceNotFoundException("Orgaisation", "email", email);
	}
	

}
