package com.service.hospital.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.service.hospital.entity.Hospital;
import com.service.hospital.exceptions.CustomExceptions;
import com.service.hospital.exceptions.ResourceNotFoundException;
import com.service.hospital.repo.HospitalRepo;
import com.service.hospital.utils.Helper;
import com.service.hospital.utils.User;

@Service
public class HospitalServiceImpls implements HospitalService {

	@Autowired
	private HospitalRepo hospitalRepository;
	
	@Override
	public Object validateUserAndGetToken(User user) throws ResourceNotFoundException {
		Hospital byEmail = checkIfHospAlreadyExist(user.getEmail());
//		if(user.getNewPassword()==null || user.getNewPassword().isEmpty())
//			throw new  ResourceNotFoundException("New Password not valid");
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
			throw new ResourceNotFoundException("Hospital", "email", user.getEmail());
	}

	@Override
	public Hospital createHospital(Hospital hospital) throws CustomExceptions, ResourceNotFoundException {
		if(checkIfHospAlreadyExist(hospital.getHospitalEmail())==null) {
			try {
//				hospital.setAgencies(Arrays.asList("aa"));
				hospital.setPassword(Helper.getEncryptedPassword(hospital.getPassword()));
				return hospitalRepository.save(hospital);
			} catch (Exception e) {
				throw new CustomExceptions("exception occured while saving employee with email : ",hospital.getHospitalEmail());
			}
		}else
		throw new CustomExceptions("Hospital Already Exist with the email : ", hospital.getHospitalEmail());
	}

	@Override
	public Hospital findHospitalByEmail(String email) throws ResourceNotFoundException {
		Hospital hospital = checkIfHospAlreadyExist(email);
		if(hospital!=null) {
		  return hospital;
		}
		throw new ResourceNotFoundException("hospital", "email", email);
	}
	

	@Override
	public Hospital deleteByEmail(String email) throws CustomExceptions, ResourceNotFoundException {
		Hospital hospital = checkIfHospAlreadyExist(email);
		if(hospital!=null) {
			try {
				hospitalRepository.delete(hospital);
				return hospital;
			} catch (Exception e) {
			  throw new CustomExceptions(e.getMessage());
			}
		}
		throw new ResourceNotFoundException("hospital", "email", email);
	}

	@Override
	public Hospital changePassword(User user) throws CustomExceptions, ResourceNotFoundException {
		Hospital hospital = checkIfHospAlreadyExist(user.getEmail());
		if(hospital!=null) {
			if(user.getPassword()!=null) {
				if(Helper.decryptPassword(hospital.getPassword()).equals(user.getPassword())) {
					hospital.setPassword(Helper.getEncryptedPassword(user.getNewPassword()));
					return  hospitalRepository.save(hospital);
				}else {
					throw new  CustomExceptions("Password did not match");
				}
			}
			throw new  CustomExceptions("Password not valid");
		}
		else
			throw new ResourceNotFoundException("hospital", "email", user.getEmail());
	}

	private Hospital checkIfHospAlreadyExist(String email) throws ResourceNotFoundException {
		if(email==null || email.isEmpty())
			throw new ResourceNotFoundException("email is not valid");
		Optional<Hospital> hospital = hospitalRepository.findByHospitalEmail(email);
		if(hospital.isPresent()) {
			return hospital.get();
		}
		return null;
	}

	@Override
	public List<Hospital> getAllHospitals() throws CustomExceptions {
		try {
			return hospitalRepository.findAll();
		} catch (Exception e) {
		throw new CustomExceptions("exception occured ::"+e.getMessage());
		}
		
	}
	@Override
	public List<Hospital> findByAgency(String email) throws ResourceNotFoundException{
		
//	   List<Hospital> hospitals = hospitalRepository.findByAgencyEmail(email);
		   List<Hospital> hospitals = hospitalRepository.findByAgency(email);
	   if(hospitals!=null && !hospitals.isEmpty())
		   return hospitals;
	   throw new ResourceNotFoundException("Hospitals", "Agency Email", email);
	}

	@Override
	public Hospital updateHospital(Hospital hosp) throws ResourceNotFoundException {
		Hospital hospFound = checkIfHospAlreadyExist(hosp.getHospitalEmail());
		if(hospFound!=null) {
		if(!validatePassword(hospFound, hosp))
			throw new ResourceNotFoundException("Password did not match");
			if(hosp.getHospitalName()!=null && !hosp.getHospitalName().isEmpty())
				hospFound.setHospitalName(hosp.getHospitalName());
			if(hosp.getAddLine1()!=null && !hosp.getAddLine1().isEmpty())
				hospFound.setAddLine1(hosp.getAddLine1());
			if(hosp.getCity()!=null && !hosp.getCity().isEmpty())
				hospFound.setCity(hosp.getCity());
			if(hosp.getZip()!=null && !hosp.getZip().isEmpty())
				hospFound.setZip(hosp.getZip());
//			if(hosp.getAgencyEmail()!=null && !hosp.getAgencyEmail().isEmpty())
//				hospFound.setAgencyEmail(hosp.getAgencyEmail());
//			orgFound.setPassword(Helper.getEncryptedPassword(org.getPassword()));
			return hospitalRepository.save(hospFound);
		}
		else 
			throw new ResourceNotFoundException("Hospital", "email", hosp.getHospitalEmail());

	}
	private boolean validatePassword(Hospital oldHosp,Hospital newHosp) {
		boolean flag = oldHosp.getPassword().equals(newHosp.getPassword());
		if(!flag)
		flag =  Helper.
				decryptPassword(
						oldHosp.getPassword()).equals(newHosp.getPassword());
		return flag;
	}

	@Override
	public Hospital addAgency(String hospEmail, String agencyEmail) throws ResourceNotFoundException {
		Hospital hospFound = checkIfHospAlreadyExist(hospEmail);
		if(hospFound!=null) {
			   Set<String> agencies = hospFound.getAgencies();
		        agencies.add(agencyEmail);
		        hospFound.setAgencies(agencies);
		        return hospitalRepository.save(hospFound);
		}
		else 
			throw new ResourceNotFoundException("Hospital", "email", hospEmail);

	}
}
