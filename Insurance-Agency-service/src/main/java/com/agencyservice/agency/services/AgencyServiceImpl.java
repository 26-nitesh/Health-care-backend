package com.agencyservice.agency.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agencyservice.agency.entity.Agency;
import com.agencyservice.agency.exceptions.CustomExceptions;
import com.agencyservice.agency.exceptions.ResourceNotFoundException;
import com.agencyservice.agency.repo.AgencyRepo;
import com.agencyservice.agency.utils.Helper;
import com.agencyservice.agency.utils.User;


@Service
public class AgencyServiceImpl implements AgencyService {

	@Autowired private AgencyRepo agencyRepositpory;
	
	@Override
	public List<Map<String, String>> getAllAgencyEmails() throws CustomExceptions {
		
//		List<Agency> agecines = agencyRepositpory.findAll();
//		agecines.stream().map(
//				agency->{agency.getAgencyEmail()
////			Map.of("Name",)
//		}).;
		try {
			return agencyRepositpory.findAll()
                    .stream()
                          .map(
                    		  agency ->
                    		            Map.of("Agency Name", agency.getAgencyName(),
                    		            		"Agency Email",agency.getAgencyEmail()))
                          .collect(Collectors.toList());
		}catch (Exception e) {
			throw new CustomExceptions("exception occured ::"+e.getMessage());

		}
		
	}

	@Override
	public Agency createNewAgency(Agency agency) throws CustomExceptions, ResourceNotFoundException {
		if(checkIfAgencyAlreadyExist(agency.getAgencyEmail())==null) {
			try {
				agency.setPassword(Helper.getEncryptedPassword(agency.getPassword()));
				return agencyRepositpory.save(agency);
			} catch (Exception e) {
				throw new CustomExceptions(e.getMessage(),agency.getAgencyEmail());
			}
		}else {

			throw new CustomExceptions("Agency Already Exist with the email : ", agency.getAgencyEmail());
		}
	}
	
	private Agency checkIfAgencyAlreadyExist(String orgEmail) throws ResourceNotFoundException {
		if(orgEmail==null || orgEmail.isEmpty())
			throw new ResourceNotFoundException("email id not valid");
		Optional<Agency> agencyByEmail = agencyRepositpory.findByAgencyEmail(orgEmail);
		if(agencyByEmail.isPresent()) {
			return agencyByEmail.get();
		}
		return null;
	}

	@Override
	public Object validateUserAndGetToken(User user) throws ResourceNotFoundException {
			Agency byEmail = checkIfAgencyAlreadyExist(user.getEmail());
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
				throw new ResourceNotFoundException("Agency", "email", user.getEmail());
		}

	@Override
	public Agency getAgencyEmail(String email) throws ResourceNotFoundException {
		Agency byEmail = checkIfAgencyAlreadyExist(email);
		if(byEmail!=null)
			return byEmail;
		else
			throw new ResourceNotFoundException("Agency", "email", email);
	}

	@Override
	public Agency changePassword(User user) throws ResourceNotFoundException, CustomExceptions {
		Agency agency = checkIfAgencyAlreadyExist(user.getEmail());
		if(user.getNewPassword()==null || user.getNewPassword().isEmpty())
			throw new  CustomExceptions("New Password not valid");
		if(agency!=null) {
			if(user.getPassword()!=null) {
				if(Helper.decryptPassword(agency.getPassword()).equals(user.getPassword())) {
					agency.setPassword(Helper.getEncryptedPassword(user.getNewPassword()));
					return  agencyRepositpory.save(agency);
				}else {
					throw new  CustomExceptions("Password did not match");
				}
			}
			throw new  CustomExceptions("Password not valid");
		}
		else
			throw new ResourceNotFoundException("Agency", "email", user.getEmail());
	}

	@Override
	public Agency updateAgency(Agency agency) throws ResourceNotFoundException {
		Agency agencyFound = checkIfAgencyAlreadyExist(agency.getAgencyEmail());
		if(!validatePassword(agencyFound, agency))
			throw new ResourceNotFoundException("Password did not match");
		if(agencyFound!=null) {
			if(agency.getAgencyName()!=null && !agency.getAgencyName().isEmpty())
				agencyFound.setAgencyName(agency.getAgencyName());
			if(agency.getAddLine1()!=null && !agency.getAddLine1().isEmpty())
				agencyFound.setAddLine1(agency.getAddLine1());
			if(agency.getCity()!=null && !agency.getCity().isEmpty())
				agencyFound.setCity(agency.getCity());
			if(agency.getZip()!=null && !agency.getZip().isEmpty())
				agencyFound.setZip(agency.getZip());
//			orgFound.setInsuranceAgencyEmail(org.getInsuranceAgencyEmail());
//			orgFound.setPassword(Helper.getEncryptedPassword(org.getPassword()));
			return agencyRepositpory.save(agencyFound);
		}
		else 
			throw new ResourceNotFoundException("Agency", "email", agency.getAgencyEmail());

	}
	
	private boolean validatePassword(Agency oldAgency,Agency newAgency) {
		return Helper.
				decryptPassword(
						oldAgency.getPassword()).equals(newAgency.getPassword());
	}
}
