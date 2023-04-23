package com.service.report.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.service.report.entity.Report;
import com.service.report.exceptions.ResourceNotFoundException;
import com.service.report.service.ReportService;
import com.service.report.util.APIResponse;


@RestController
@RequestMapping("report/api")
@CrossOrigin("*")
public class ReportRestController {

	@Autowired ReportService reportService;
	
	@PostMapping("/create-report")
	public ResponseEntity<Object> createNewReport(@RequestBody Report report){
		try {
			return 
				APIResponse.
					generateResponse(
							HttpStatus.CREATED.name(),
							HttpStatus.CREATED, 
							reportService.createReport(report)
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
	
	@GetMapping("/get/{appointmentId}")
	public ResponseEntity<Object> getByAppId(@PathVariable int appointmentId){
		try {
			return 
				APIResponse.
					generateResponse(
							HttpStatus.FOUND.name(),
							HttpStatus.OK, 
							reportService.findByAppId(appointmentId)
							);
		} catch (ResourceNotFoundException e) {
			return 
				APIResponse.
				    generateResponse(
					         e.getMessage(),
					         HttpStatus.BAD_REQUEST,
					         null
					          );
		}
		
	}
	@DeleteMapping("/delete/{appointmentId}")
	public ResponseEntity<Object> deleteByAppId(@PathVariable int appointmentId){
		try {
			return 
				APIResponse.
					generateResponse(
							"Deleted Sucessfully",
							HttpStatus.OK, 
							reportService.deleteByAppId(appointmentId)
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
	
	@PutMapping("/update-report")
	public ResponseEntity<Object> updateReport(@RequestBody Report report){
		try {
			return 
				APIResponse.
					generateResponse(
							"Updated Sucessfully",
							HttpStatus.OK, 
							reportService.updateReport(report)
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
	
//	@PutMapping("/")
	
}
