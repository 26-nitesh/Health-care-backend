package com.service.hospital.controller;

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

import com.service.hospital.entity.Hospital;
import com.service.hospital.exceptions.CustomExceptions;
import com.service.hospital.exceptions.ResourceNotFoundException;
import com.service.hospital.service.HospitalService;
import com.service.hospital.utils.APIResponse;
import com.service.hospital.utils.User;

@RestController
@RequestMapping("/api/hospital")
public class HospitalRestController {

	@Autowired HospitalService hospitalService;
	
	@PostMapping("/login")
	public ResponseEntity<Object> validateUserAndGenerateToken(@RequestBody User user){
		try {
			return 
				APIResponse.
					generateResponse(
							"Sucess",
							HttpStatus.OK, 
							hospitalService.validateUserAndGetToken(user)
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
	
	@PostMapping("public/add-hospital")
	public ResponseEntity<Object> createNewEmployee(@RequestBody Hospital hospital){
		try {
			return 
				APIResponse.
					generateResponse(
							HttpStatus.CREATED.name(),
							HttpStatus.CREATED, 
							hospitalService.createHospital(hospital)
							);
		} catch (CustomExceptions |ResourceNotFoundException e) {
			return 
				APIResponse.
				    generateResponse(
					         e.getMessage(),
					         HttpStatus.CONFLICT,
					         null
					          );
		}
		
	}
	

	@GetMapping("/{email}")
	public ResponseEntity<Object> findByEmail(@PathVariable String email) throws ResourceNotFoundException{
		try {
			return 
					APIResponse.generateResponse(
							HttpStatus.FOUND.name(),
							HttpStatus.OK,
							hospitalService.findHospitalByEmail(email)
							);
		} catch (ResourceNotFoundException e) {
			return 
					APIResponse.generateResponse(
							e.getMessage(),
							HttpStatus.NOT_FOUND,
							null);
		}
		
	}
	
	@DeleteMapping("/delete/{email}")
	public ResponseEntity<Object> deleteByEmail(@PathVariable String email){
			try {
				return 
						APIResponse.generateResponse(
								"deleted sucessfully",
								HttpStatus.OK,
								hospitalService.deleteByEmail(email)
								);
			}
//				catch (CustomExceptions e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		} 
		catch (Exception e) {
			return 
					APIResponse.generateResponse(
							e.getMessage(),
							HttpStatus.BAD_REQUEST,
							null);
		}
		
	}
	
	@PutMapping("/changePassword")
	public ResponseEntity<Object> changePsssword(@RequestBody User user){
		try {
			hospitalService.changePassword(user);
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
	@GetMapping("/agency/{email}")
	public ResponseEntity<Object> findByAgency(@PathVariable String email) throws ResourceNotFoundException{
		try {
			return 
					APIResponse.generateResponse(
							HttpStatus.FOUND.name(),
							HttpStatus.OK,
							hospitalService.findByAgency(email)
							);
		} catch (ResourceNotFoundException e) {
			return 
					APIResponse.generateResponse(
							e.getMessage(),
							HttpStatus.NOT_FOUND,
							null);
		}
		
	}
	@GetMapping("/")
	public ResponseEntity<Object> findAll(@PathVariable String email) throws ResourceNotFoundException{
		try {
			return 
					APIResponse.generateResponse(
							HttpStatus.FOUND.name(),
							HttpStatus.OK,
							hospitalService.getAllHospitals()
							);
		} catch (CustomExceptions e) {
			return 
					APIResponse.generateResponse(
							e.getMessage(),
							HttpStatus.NOT_FOUND,
							null);
		}
		
	}
}
