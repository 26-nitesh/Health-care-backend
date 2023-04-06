package com.service.organisation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.service.organisation.entity.Organisation;
import com.service.organisation.exceptions.CustomExceptions;
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
}
