package com.policyservice.service;

import java.util.List;

import com.policyservice.entity.Policy;
import com.policyservice.exceptions.CustomExceptions;
import com.policyservice.exceptions.ResourceNotFoundException;


public interface PolicyService {

	Policy createPolicy(Policy policy) throws CustomExceptions, ResourceNotFoundException;

	void deletePolicyByEmail(String email) throws ResourceNotFoundException;

	List<Policy> getAllPolicyByOrgEmail(String email) throws ResourceNotFoundException;

	Policy updatePolicy(String orgEmail, Policy policy) throws CustomExceptions, ResourceNotFoundException;

	void deletePolicyByID(int id) throws ResourceNotFoundException;

	
}
