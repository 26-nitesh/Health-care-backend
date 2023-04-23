package com.service.organisation.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.service.organisation.entity.Organisation;
import com.service.organisation.exceptions.CustomExceptions;
import com.service.organisation.exceptions.ResourceNotFoundException;
import com.service.organisation.repositories.OrgRepository;
import com.service.organisation.util.Helper;
import com.service.organisation.util.OrgServiceLogger;
import com.service.organisation.util.User;

@Service
public class OrgServiceImpl implements OrgService{

	@Autowired private OrgRepository orgRepo;
	@Override
	public Organisation createOrg(Organisation organisation) throws CustomExceptions, ResourceNotFoundException {
		if(checkIfOrgAlreadyExist(organisation.getOrganisationEmail())==null) {
			try {
				organisation.setPassword(Helper.getEncryptedPassword(organisation.getPassword()));
				OrgServiceLogger.log.info("saving org with email {}",organisation.getOrganisationEmail());
				return orgRepo.save(organisation);
			} catch (Exception e) {
				OrgServiceLogger.log.error("exception occurred @@@ {}",e.getMessage());
				throw new CustomExceptions(e.getMessage(),organisation.getOrganisationEmail());
			}
		}else {

			OrgServiceLogger.log.error("org already exist @@@ {}",organisation.getOrganisationEmail());
			throw new CustomExceptions("Organisation Already Exist with the email : ", organisation.getOrganisationEmail());
		}
	}
	private Organisation checkIfOrgAlreadyExist(String orgEmail) throws ResourceNotFoundException {
		if(orgEmail==null || orgEmail.isEmpty())
			throw new ResourceNotFoundException("email id not valid");
		Optional<Organisation> orgByEmail = orgRepo.findByOrganisationEmail(orgEmail);
		if(orgByEmail.isPresent()) {
			return orgByEmail.get();
		}
		return null;
	}
	@Override
	public Organisation getByEmail(String email) throws ResourceNotFoundException {
		Organisation byEmail = checkIfOrgAlreadyExist(email);
		   if(byEmail!=null) {
			   return byEmail;
		   }
			throw new ResourceNotFoundException("Orgaisation", "email", email);
		}
	@Override
	public List<Organisation> getByAgencyEmail(String email) throws CustomExceptions {
		List<Organisation> Orgs = orgRepo.findByInsuranceAgencyEmail(email);
		if(!Orgs.isEmpty()) {
			return Orgs;
		}
		throw new CustomExceptions("Organisations Not Found with agency the email : ", email);
	}
	@Override
	public List<Organisation> deleteByAgencyEmail(String email) throws CustomExceptions {
		List<Organisation> Orgs = getByAgencyEmail(email);
		if(!Orgs.isEmpty()) {
//		List<Integer> ids = Orgs.stream().map(o->o.getOrgId()).collect(Collectors.toList());
//		orgRepo.deleteAllById(ids);
			orgRepo.deleteAllByInsuranceAgencyEmail(email);
			return Orgs;
		}
		throw new CustomExceptions("Organisations Not Found with agency the email : ", email);
	}
//	@Override
//	public List<Integer> findPolicyIdsByEmail(String email) throws ResourceNotFoundException {
//		Organisation byEmail = checkIfOrgAlreadyExist(email);
//		   if(byEmail!=null) {
//			   return byEmail.getPolicyIds();
//		   }
//			throw new ResourceNotFoundException("Orgaisation", "email", email);
//		}
	@Override
	public Organisation deleteByEmail(String email) throws ResourceNotFoundException {
		Organisation byEmail = checkIfOrgAlreadyExist(email);
		   if(byEmail!=null) {
			   orgRepo.deleteById(byEmail.getOrgId());
			   return byEmail;
		   }
			throw new ResourceNotFoundException("Orgaisation", "email", email);
	}
	@Override
	public Organisation changePassword(User user) throws ResourceNotFoundException, CustomExceptions {
		Organisation org = checkIfOrgAlreadyExist(user.getEmail());
		if(org!=null) {
			if(user.getNewPassword()==null || user.getNewPassword().isEmpty())
				throw new  CustomExceptions("New Password not valid");
			if(user.getPassword()!=null) {
				if(Helper.decryptPassword(org.getPassword()).equals(user.getPassword())) {
					org.setPassword(Helper.getEncryptedPassword(user.getNewPassword()));
					return  orgRepo.save(org);
				}else {
					throw new  CustomExceptions("Password did not match");
				}
			}
			throw new  CustomExceptions("Password not valid");
		}
		else
			throw new ResourceNotFoundException("Employee", "email", user.getEmail());
	}
	@Override
	public Organisation updateOrganisation(Organisation org) throws ResourceNotFoundException {
 
		OrgServiceLogger.log.info("request came for update  org {}",org.getOrganisationEmail());
		Organisation orgFound = checkIfOrgAlreadyExist(org.getOrganisationEmail());
		OrgServiceLogger.log.info("org forund for update  org {}",orgFound.getOrganisationEmail());
		System.out.println(validatePassword(orgFound, org));
		if(orgFound!=null) {
		if(!validatePassword(orgFound, org)) {
			 OrgServiceLogger.log.debug("validation failed");
			throw new ResourceNotFoundException("Password did not match");
       
		}
		OrgServiceLogger.log.debug("Password validation Passed");
			OrgServiceLogger.log.info("going to update org {}",org.getOrganisationEmail());
			if(org.getOrganisationName()!=null && !org.getOrganisationName().isEmpty())
			orgFound.setOrganisationName(org.getOrganisationName());
			if(org.getAddLine1()!=null && !org.getAddLine1().isEmpty())
			orgFound.setAddLine1(org.getAddLine1());
			if(org.getCity()!=null && !org.getCity().isEmpty())
			orgFound.setCity(org.getCity());
			if(org.getZip()!=null && !org.getZip().isEmpty())
			orgFound.setZip(org.getZip());
			if(org.getInsuranceAgencyEmail()!=null && !org.getInsuranceAgencyEmail().isEmpty())
			orgFound.setInsuranceAgencyEmail(org.getInsuranceAgencyEmail());
//			orgFound.setPassword(Helper.getEncryptedPassword(org.getPassword()));
			OrgServiceLogger.log.info("updating org {}",org.getOrganisationEmail());
			return orgRepo.save(orgFound);
		}
		else 
			throw new ResourceNotFoundException("Organisation", "email", org.getOrganisationName());
	
	}
	private boolean validatePassword(Organisation oldOrg, Organisation newOrg) {
		boolean flag = oldOrg.getPassword().equals(newOrg.getPassword());
		if(!flag)
		flag =  Helper.
				decryptPassword(
						oldOrg.getPassword()).equals(newOrg.getPassword());
		return flag;
	}
	@Override
	public Object validateUserAndGetToken(User user) throws ResourceNotFoundException {
		Organisation byEmail = checkIfOrgAlreadyExist(user.getEmail());
		if(byEmail!=null) {
			if(user.getPassword()==null && user.getPassword().isEmpty()) {
				throw new ResourceNotFoundException("password not present");
			}
			else if(Helper.decryptPassword(byEmail.getPassword()).equals(user.getPassword())) {
				return Helper.genrateJwtTokken(user);
			}else {
				throw new ResourceNotFoundException("wrong password");
			}
		}
		else
			throw new ResourceNotFoundException("Organisation", "email", user.getEmail());
	}
	@Override
	public List<Map<String, String>> getAllOrgEmails() throws CustomExceptions {
		try {
			return orgRepo.findAll().
					stream().map(
							org
							    ->
							Map.of("Org Name", org.getOrganisationName(),
        		            		"Org Email",org.getOrganisationEmail()))
					                           .collect(Collectors.toList()
					                                  );
		} catch (Exception e) {
		throw new CustomExceptions("exception occured ::"+e.getMessage());
		}
		
	}
	@Override
	public List<Organisation> getAllOrgs() throws CustomExceptions {
		// TODO Auto-generated method stub
		return orgRepo.findAll();
	}
	

}
