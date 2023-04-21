package com.service.hospital.service;

import java.util.List;

import com.service.hospital.entity.Hospital;
import com.service.hospital.exceptions.CustomExceptions;
import com.service.hospital.exceptions.ResourceNotFoundException;
import com.service.hospital.utils.User;

public interface HospitalService {

	Object validateUserAndGetToken(User user) throws ResourceNotFoundException;

	Hospital createHospital(Hospital hospital) throws CustomExceptions,ResourceNotFoundException;

	Hospital findHospitalByEmail(String email) throws ResourceNotFoundException;

	Hospital deleteByEmail(String email) throws CustomExceptions,ResourceNotFoundException;

	Hospital changePassword(User user) throws CustomExceptions,ResourceNotFoundException;

	List<Hospital> findByAgency(String email) throws ResourceNotFoundException;

	List<Hospital> getAllHospitals() throws CustomExceptions;

	Hospital updateHospital(Hospital hosp) throws ResourceNotFoundException;

}
