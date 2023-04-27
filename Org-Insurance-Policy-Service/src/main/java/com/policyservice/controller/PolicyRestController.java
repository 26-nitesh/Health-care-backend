package com.policyservice.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.policyservice.entity.Policy;
import com.policyservice.exceptions.CustomExceptions;
import com.policyservice.exceptions.ResourceNotFoundException;
import com.policyservice.service.PolicyService;
import com.policyservice.utils.APIResponse;
import com.policyservice.utils.PolicyServiceLogger;


@RestController
@RequestMapping("/policy/api")
@CrossOrigin("*")
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
							policyService.createPolicy(policy));
		} catch (Exception e) {
			PolicyServiceLogger.log.error("exception in creating new Policy {}",e.getMessage());
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
	public ResponseEntity<Object> updatePolicyByPolicyName(@PathVariable String  orgEmail,
			                                         @RequestBody Policy policy){
		try {
			policyService.updatePolicy(orgEmail,policy);
			
			return 
				APIResponse.
					generateResponse(
							"Updated sucessfully",
							HttpStatus.OK, 
							policy
							);
		} catch (CustomExceptions | ResourceNotFoundException e  ) {
			PolicyServiceLogger.log.error("exception in updating  Policy {}",e.getMessage());

			return 
				APIResponse.
				generateResponse(
					e.getMessage(),
					HttpStatus.BAD_REQUEST,
					null
					);
		}
}
	
	
	@DeleteMapping("/delete-all-policy/{email}")
	public ResponseEntity<Object> deletePolicyByOrg(@PathVariable String email){
		try {
			policyService.deletePolicyByEmail(email);
			PolicyServiceLogger.log.info("policy deleted");
         return 
				APIResponse.
					generateResponse(
							"deleted sucessfully",
							HttpStatus.OK, 
							null);
		} catch (Exception e) {
			return 
				APIResponse.
				generateResponse(
					e.getMessage(),
					HttpStatus.NOT_FOUND,
					null
					);
		}
}
	@DeleteMapping("/deleteById/{id}")
	public ResponseEntity<Object> deletePolicyById(@PathVariable int id){
		try {
			policyService.deletePolicyByID(id);
			PolicyServiceLogger.log.info("policy deleted");
         return 
				APIResponse.
					generateResponse(
							"deleted sucessfully",
							HttpStatus.OK, 
							null);
		} catch (Exception e) {
			return 
				APIResponse.
				generateResponse(
					e.getMessage(),
					HttpStatus.NOT_FOUND,
					null
					);
		}
}

	@GetMapping("/get-all-policy/{email}")
	public ResponseEntity<Object> getAllPolicyByOrg(@PathVariable String email){
		try {
			return 
				APIResponse.
					generateResponse(
							HttpStatus.FOUND.name(),
							HttpStatus.OK, 
							policyService.getAllPolicyByOrgEmail(email));
		} catch (ResourceNotFoundException e) {
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
