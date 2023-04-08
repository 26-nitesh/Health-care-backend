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
	
	

}
