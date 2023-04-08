package com.service.organisation.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.service.organisation.entity.Organisation;
import com.service.organisation.exceptions.CustomExceptions;
import com.service.organisation.exceptions.ResourceNotFoundException;
import com.service.organisation.services.OrgService;
import com.service.organisation.util.APIResponse;
import com.service.organisation.util.OrgServiceLogger;
import com.service.organisation.util.User;

@RestController
@RequestMapping("/api/organisation")
public class OrgRestController {

	@Autowired private OrgService orgService;
	
	
	@PostMapping("/login")
	public ResponseEntity<Object> validateUserAndGenerateToken(@RequestBody User user){
		try {
			OrgServiceLogger.log.info("request for validating user");
			return 
				APIResponse.
					generateResponse(
							"Sucess",
							HttpStatus.OK, 
							orgService.validateUserAndGetToken(user)
							);
		} catch (Exception e) {
			return 
					APIResponse.
					generateResponse(
						e.getMessage(),
						HttpStatus.UNAUTHORIZED,
						null
						);
		}
	}
	
	
	@GetMapping("/all")
	public ResponseEntity<Object> getAllOrgs(){
		try {
			OrgServiceLogger.log.info("request for validating user");
			return 
				APIResponse.
					generateResponse(
							"Sucess",
							HttpStatus.OK, 
							orgService.getAllOrgEmails()
							);
		} catch (Exception e) {
			return 
					APIResponse.
					generateResponse(
						e.getMessage(),
						HttpStatus.BAD_GATEWAY,
						null
						);
		}
	}
	
	@PostMapping("/addOrg")
	public ResponseEntity<Object> createOrganisation(@RequestBody @Valid Organisation organisation){
		try {
			OrgServiceLogger.log.info("request came for creation of new organisation");
			return 
				APIResponse.
					generateResponse(
							HttpStatus.CREATED.name(),
							HttpStatus.CREATED, 
							orgService.createOrg(organisation)
							);
		} catch (CustomExceptions | ResourceNotFoundException e) {
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
							HttpStatus.OK, 
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
	@DeleteMapping("delete-org/{email}")
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
							HttpStatus.OK, 
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
	

	@PutMapping("/changePassword")
	public ResponseEntity<Object> changePAssword(@RequestBody User user){
		try {
			orgService.changePassword(user);
			return 
				APIResponse.
					generateResponse(
							"Password changed sucessfully",
							HttpStatus.OK, 
							null
							);
		} catch (CustomExceptions | ResourceNotFoundException e) {
			return 
				APIResponse.
				    generateResponse(
					         e.getMessage(),
					         HttpStatus.BAD_REQUEST,
					         null
					          );
		}
	
}
	
	@PutMapping("/update-org")
	public ResponseEntity<Object> update(@RequestBody Organisation org){
		try {
			return 
				APIResponse.
					generateResponse(
							"updated sucessfully",
							HttpStatus.OK, 
							orgService.updateOrganisation(org)
							);
		} catch ( ResourceNotFoundException e) {
			return 
				APIResponse.
				    generateResponse(
					         e.getMessage(),
					         HttpStatus.BAD_REQUEST,
					         null
					          );
		}
	}
//	@GetMapping("policy/{email}")
//	public ResponseEntity<Object> getPolicyIds(@PathVariable String email){
//		try {
//			return 
//				APIResponse.
//					generateResponse(
//							HttpStatus.FOUND.name(),
//							HttpStatus.OK, 
//							orgService.findPolicyIdsByEmail(email)
//							);
//		} catch (ResourceNotFoundException e) {
//			return 
//				APIResponse.
//				generateResponse(
//					e.getMessage(),
//					HttpStatus.NOT_FOUND,
//					null
//					);
//		}
//		
//	}

}
