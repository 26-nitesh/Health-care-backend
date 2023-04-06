package com.service.policy.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.service.policy.entity.Policy;
import com.service.policy.exceptions.CustomExceptions;
import com.service.policy.service.PolicyService;
import com.service.policy.service.utils.APIResponse;
import com.service.policy.service.utils.PolicyKV;

@RestController
@RequestMapping("/policy")
public class PolicyRestController {

@Autowired PolicyService policyService;

	@PostMapping("/create-policy")
	public ResponseEntity<Object> createPolicy(@RequestBody Policy policy){
		try {
			return 
				APIResponse.
					generateResponse(
							HttpStatus.CREATED.name(),
							HttpStatus.CREATED, 
							policyService.createPolicy(policy)
							);
		} catch (Exception e) {
			return 
				APIResponse.
				generateResponse(
					e.getMessage(),
					HttpStatus.BAD_REQUEST,
					null
					);
		}
}
	
	@PutMapping("/update-policy/{orgEmail}")
	public ResponseEntity<Object> updatePolicyByPolicyName(@PathVariable String  orgEmail,@RequestBody PolicyKV kv){
		try {
			Object policy = policyService.updatePolicy(orgEmail,kv);
			if(policy==null) {
				return 
						APIResponse.
							generateResponse(
									"Policy Key Not Found",
									HttpStatus.BAD_REQUEST, 
									null
									);
			}
			return 
				APIResponse.
					generateResponse(
							"Updated sucessfully",
							HttpStatus.OK, 
							Map.of("email", orgEmail, kv.getPolicyKey(), kv.getPolicyValue())
							);
		} catch (CustomExceptions e) {
			return 
				APIResponse.
				generateResponse(
					e.getMessage(),
					HttpStatus.NOT_FOUND,
					null
					);
		}
}
}
