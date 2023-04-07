package com.service.organisation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.service.organisation.entity.Organisation;
import com.service.organisation.exceptions.CustomExceptions;
import com.service.organisation.exceptions.ResourceNotFoundException;
import com.service.organisation.services.OrgService;
import com.service.organisation.util.APIResponse;

@RestController
@RequestMapping("/api/organisation")
public class OrgRestController {

	@Autowired private OrgService orgService;
	
	@PostMapping("/addOrg")
	public ResponseEntity<Object> createOrganisation(@RequestBody Organisation organisation){
		try {
			return 
				APIResponse.
					generateResponse(
							HttpStatus.CREATED.name(),
							HttpStatus.CREATED, 
							orgService.createOrg(organisation)
							);
		} catch (CustomExceptions e) {
			return 
				APIResponse.
				generateResponse(
					e.getMessage(),
					HttpStatus.CONFLICT,
					null
					);
		}
		
	}
	@GetMapping("org/{email}")
	public ResponseEntity<Object> getOrganisationByEmail(@PathVariable String email){
		try {
			return 
				APIResponse.
					generateResponse(
							HttpStatus.FOUND.name(),
							HttpStatus.FOUND, 
							orgService.getByEmail(email)
							);
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
	@GetMapping("delete-org/{email}")
	public ResponseEntity<Object> deleteOrganisationByEmail(@PathVariable String email){
		try {
			return 
				APIResponse.
					generateResponse(
							"Delete SucesFully",
							HttpStatus.OK, 
							orgService.deleteByEmail(email)
							);
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
	@GetMapping("insurance/{email}")
	public ResponseEntity<Object> getByAgencyEmail(@PathVariable String email){
		try {
			return 
				APIResponse.
					generateResponse(
							HttpStatus.FOUND.name(),
							HttpStatus.FOUND, 
							orgService.getByAgencyEmail(email)
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
	
	@DeleteMapping("deleteByAgeciy/{email}")
	public ResponseEntity<Object> deleteByAgencyEmail(@PathVariable String email){
		try {
			return 
				APIResponse.
					generateResponse(
							"Delete SucesFully",
							HttpStatus.OK, 
							orgService.deleteByAgencyEmail(email)
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
	
	@GetMapping("policy/{email}")
	public ResponseEntity<Object> getPolicyIds(@PathVariable String email){
		try {
			return 
				APIResponse.
					generateResponse(
							HttpStatus.FOUND.name(),
							HttpStatus.FOUND, 
							orgService.findPolicyIdsByEmail(email)
							);
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
