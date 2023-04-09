package com.service.hospital.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
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
		if(user.getNewPassword()==null || user.getNewPassword().isEmpty())
			throw new  ResourceNotFoundException("New Password not valid");
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
	   List<Hospital> hospitals = hospitalRepository.findByAgencyEmail(email);
	   if(hospitals!=null && !hospitals.isEmpty())
		   return hospitals;
	   throw new ResourceNotFoundException("Hospitals", "Agency Email", email);
	}
}
