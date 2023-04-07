package com.service.policy.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.service.policy.entity.Policy;
import com.service.policy.exceptions.CustomExceptions;
import com.service.policy.repo.PolicyRepo;
import com.service.policy.service.utils.PolicyKV;

@Service
public class PolicyServiceImpl implements PolicyService{

	@Autowired PolicyRepo policyRepo;
	@Override
	public Policy createPolicy(Policy policy) {
		return policyRepo.save(policy);
	}
	
	
	
	@Override
	public Object updatePolicy(String email,PolicyKV kv) throws CustomExceptions {
		List<Policy> policies = findByEmail(email);
        Map<String, Boolean> status = new HashMap<>();
        status.put("updated", false);
        
		policies.forEach(policy->{
			Map<String, String> oldKV = policy.getPolicyKeyValue();
			if(oldKV.containsKey(kv.getPolicyKey())) {
				oldKV.put(kv.getPolicyKey(), kv.getPolicyValue());
				policyRepo.save(policy);
				status.put("updated", true);
				return;
			}
		});
//		policies.forEach(po->{
//			po.getPolicyKeyValue().keySet().forEach(key->{
//				if(key.equalsIgnoreCase(kv.getPolicyKey())) {
//					po.getPolicyKeyValue().put(key, kv.getPolicyValue());
//					policyRepo.save(po);
//					return;
//				}
//			});
//		});
		if(status.get("updated")) {
			return "";
			
		}return null;
	}



	@Override
	public List<Policy> findByEmail(String email) throws CustomExceptions {
		List<Policy> policies = policyRepo.findByOrgEmail(email);
		if(!policies.isEmpty()) {
			return policies;
		}
      throw new CustomExceptions("policy not found with email : ", email);
	}



	@Override
	public List<Policy> deletePolicyByEmail(String email) throws CustomExceptions {
		List<Policy> policies = findByEmail(email);
		policyRepo.deleteAll(policies);
		return policies;
	}

}
