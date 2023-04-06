package com.service.policy.service;

import java.util.List;

import com.service.policy.entity.Policy;
import com.service.policy.service.utils.PolicyKV;

public interface PolicyService {

	Policy createPolicy(Policy policy);

	List<Policy> findByEmail(String email);

	Object updatePolicy(String orgEmail, PolicyKV kv);

	List<Policy> deletePolicyByEmail(String email);
}
