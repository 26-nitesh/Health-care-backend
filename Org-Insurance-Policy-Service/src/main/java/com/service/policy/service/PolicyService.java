package com.service.policy.service;

import java.util.List;

import com.service.policy.entity.Policy;
import com.service.policy.exceptions.CustomExceptions;
import com.service.policy.service.utils.PolicyKV;

public interface PolicyService {

	Policy createPolicy(Policy policy);

	List<Policy> findByEmail(String email) throws CustomExceptions;

	Object updatePolicy(String orgEmail, PolicyKV kv) throws CustomExceptions;

	List<Policy> deletePolicyByEmail(String email) throws CustomExceptions;
}
