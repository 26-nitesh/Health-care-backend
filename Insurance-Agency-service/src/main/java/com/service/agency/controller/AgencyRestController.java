package com.service.agency.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.service.agency.services.AgencyService;
import com.service.agency.utils.APIResponse;


@RestController
@RequestMapping("/api/agency")
public class AgencyRestController {

	@Autowired AgencyService agencyService;
	
	@GetMapping("/all")
	public ResponseEntity<Object> getAllOrgs(){
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
}
