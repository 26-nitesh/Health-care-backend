package com.service.agency.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.service.agency.repo.AgencyRepo;

public class AgencyServiceImpl implements AgencyService {

	@Autowired private AgencyRepo agencyRepositpory;
	
	@Override
	public List<String> getAllAgencyEmails() {
		
		return agencyRepositpory.findAll()
		                        .stream()
		                              .map(
		                            		  agency ->
		                            		         agency.getAgencyEmail())
		                                                                   .collect(Collectors.toList());
		
	}

}
