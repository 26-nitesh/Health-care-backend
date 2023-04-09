package com.agencyservice.agency.services;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.agencyservice.agency.entity.Agency;
import com.agencyservice.agency.exceptions.CustomExceptions;
import com.agencyservice.agency.exceptions.ResourceNotFoundException;
import com.agencyservice.agency.utils.User;

@Service
public interface AgencyService {

	List<Map<String, String>> getAllAgencyEmails() throws CustomExceptions;

	Agency createNewAgency(Agency agency) throws CustomExceptions, ResourceNotFoundException;

	Object validateUserAndGetToken(User user) throws ResourceNotFoundException;

	Agency getAgencyEmail(String email) throws ResourceNotFoundException;

	Agency changePassword(User user) throws ResourceNotFoundException, CustomExceptions;

}
