package com.agencyservice.agency.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agencyservice.agency.entity.Agency;
import com.agencyservice.agency.exceptions.CustomExceptions;
import com.agencyservice.agency.exceptions.ResourceNotFoundException;
import com.agencyservice.agency.services.AgencyService;
import com.agencyservice.agency.utils.APIResponse;
import com.agencyservice.agency.utils.User;




@RestController
@RequestMapping("/agency/api")
@CrossOrigin("*")
public class AgencyRestController {

	@Autowired AgencyService agencyService;
	
	@GetMapping("/all")
	public ResponseEntity<Object> getAllAgency(){
		try {
			return 
				APIResponse.
					generateResponse(
							"Sucess",
							HttpStatus.OK, 
							agencyService.getAllAgencyEmails()
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
	
	@GetMapping("/{email}")
	public ResponseEntity<Object> getAgencyByEmail(@PathVariable String email){
		try {
			return 
				APIResponse.
					generateResponse(
							HttpStatus.FOUND.name(),
							HttpStatus.OK, 
							agencyService.getAgencyEmail(email)
							);
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
	@PostMapping("/create")
	public ResponseEntity<Object> createAgency(@RequestBody Agency agency){
		try {
			return 
				APIResponse.
					generateResponse(
							HttpStatus.CREATED.name(),
							HttpStatus.CREATED, 
							agencyService.createNewAgency(agency)
							);
		} catch (Exception e) {
			return 
					APIResponse.
					generateResponse(
						e.getMessage(),
						HttpStatus.CONFLICT,
						null
						);
		}
	}
	@PostMapping("/login")
	public ResponseEntity<Object> validateUserAndGenerateToken(@RequestBody User user){
		try {
			return 
				APIResponse.
					generateResponse(
							"Sucess",
							HttpStatus.OK, 
							agencyService.validateUserAndGetToken(user)
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
	
	@PutMapping("/changePassword")
	public ResponseEntity<Object> changePAssword(@RequestBody User user){
		try {
			agencyService.changePassword(user);
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
	
	@PutMapping("/update-agency")
	public ResponseEntity<Object> update(@RequestBody Agency agency){
		try {
			return 
				APIResponse.
					generateResponse(
							"updated sucessfully",
							HttpStatus.OK, 
							agencyService.updateAgency(agency)
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
	
}
