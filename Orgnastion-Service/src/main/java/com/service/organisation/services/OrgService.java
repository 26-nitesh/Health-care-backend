package com.service.organisation.services;

import java.util.List;

import com.service.organisation.entity.Organisation;
import com.service.organisation.exceptions.CustomExceptions;
import com.service.organisation.exceptions.ResourceNotFoundException;

public interface OrgService {

	Organisation createOrg(Organisation organisation) throws CustomExceptions;

	Organisation getByEmail(String email) throws ResourceNotFoundException;

	List<Organisation> getByAgencyEmail(String email) throws CustomExceptions;

	List<Organisation> deleteByAgencyEmail(String email) throws CustomExceptions;

	List<Integer> findPolicyIdsByEmail(String email) throws ResourceNotFoundException;

	Organisation deleteByEmail(String email) throws ResourceNotFoundException;

}
