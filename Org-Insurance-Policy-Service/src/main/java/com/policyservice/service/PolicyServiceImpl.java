package com.policyservice.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.policyservice.entity.Policy;
import com.policyservice.exceptions.CustomExceptions;
import com.policyservice.exceptions.ResourceNotFoundException;
import com.policyservice.repo.PolicyRepo;
import com.policyservice.utils.PolicyServiceLogger;


@Service
public class PolicyServiceImpl implements PolicyService{

	@Autowired PolicyRepo policyRepo;

	@Override
	public Policy createPolicy(Policy policy) throws CustomExceptions, ResourceNotFoundException {
		Policy found = checkIfPolicyExist(policy.getOrgEmail(),policy.getPolicyName());
		if(found==null) {
			try {
				return policyRepo.save(policy);
			}catch (Exception e) {
				throw  new CustomExceptions("exception occured while creating policy", e.getMessage());
			}
		}
		PolicyServiceLogger.log.error("Policy Already Exist");
		throw new ResourceNotFoundException("Policy Already Exist with given name : "+policy.getPolicyName());
	}

	private Policy checkIfPolicyExist(String orgEmail, String policyName) {
		Optional<Policy> policy = policyRepo.findByOrgEmailAndPolicyName(orgEmail, policyName);
		if(policy.isPresent())
			return policy.get();
		PolicyServiceLogger.log.info("Policy Not Present {}",orgEmail);
		return null;
	}

	@Override
	public void deletePolicyByEmail(String email) throws ResourceNotFoundException {
		policyRepo.deleteAll(getAllPolicyByOrgEmail(email));
	}

	@Override
	public List<Policy> getAllPolicyByOrgEmail(String email) throws ResourceNotFoundException {
	List<Policy> policies = policyRepo.findByOrgEmail(email);
	if(policies!=null && policies.isEmpty())
		throw new ResourceNotFoundException("policies","email",email);
	return policies;
	}

	@Override
	public Policy updatePolicy(String orgEmail, Policy policy) throws CustomExceptions, ResourceNotFoundException {
		List<Policy> policies = getAllPolicyByOrgEmail(orgEmail);
//		List<Policy>  updatedL = new ArrayList<>();
		if(policies ==null || policies.isEmpty()) {
			throw new CustomExceptions("policy not present for email "+orgEmail);
		} 
		Optional<Policy> ploicio = policies.stream().filter(p->p.getPolicyName().
				                        equals(policy.getPolicyName())).findFirst();
//		                                                           .
//		                                                               ifPresentOrElse(present->{
//			                                                   present.setAge(policy.getAge());
//			                                                   present.setFrequency(policy.getFrequency());
//			                                                   present.setMinMonthOfService(policy.getMinMonthOfService());
//			                                                  Policy updated = policyRepo.save(present);
//			                                                  updatedL.add(updated);
//		                                           }, ()->  new CustomExceptions("Policy NAme not present"));
		PolicyServiceLogger.log.info("Policy found  to update  {}",policy.getPolicyName());

		if(ploicio.isPresent()) {
			Policy found = ploicio.get();
			found.setAge(policy.getAge());
			found.setFrequency(policy.getFrequency());
			found.setMinMonthOfService(policy.getMinMonthOfService());
			return policyRepo.save(found);
		}
		throw new CustomExceptions("Policy NAme not present");
//			return updatedL.get(0);
	}
	

}
