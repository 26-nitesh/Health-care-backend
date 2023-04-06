package com.service.appointment.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.service.appointment.entity.AppointMent;
import com.service.appointment.exceptions.CustomExceptions;
import com.service.appointment.service.AppointmentService;
import com.service.appointment.utils.APIResponse;


@RestController
@RequestMapping("/api/appointment")
public class AppointMentRestController {

	@Autowired AppointmentService appointmentService;
	
	@PostMapping("/create-new-appointment")
	public ResponseEntity<Object> createNewAppointment(@RequestBody AppointMent appointment){
		try {
			return 
				APIResponse.
					generateResponse(
							HttpStatus.CREATED.name(),
							HttpStatus.CREATED, 
							appointmentService.createnewAppointMent(appointment)
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
	
	
	@GetMapping("/getByEmp/{email}")
	public ResponseEntity<Object> findByEmp(@PathVariable String email){
		try {
			return 
				APIResponse.
					generateResponse(
							HttpStatus.FOUND.name(),
							HttpStatus.FOUND, 
							appointmentService.findByEmpEmail(email)
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
	@DeleteMapping("/deleteByEmp/{email}")
	public ResponseEntity<Object> deleteByEmp(@PathVariable String email){
		try {
			return 
				APIResponse.
					generateResponse(
							"Deleted Suceesfully",
							HttpStatus.OK, 
							appointmentService.deleteByEmpEmail(email)
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
	
	@GetMapping("/getByHospital/{email}")
	public ResponseEntity<Object> findByHosp(@PathVariable String email){
		try {
			return 
				APIResponse.
					generateResponse(
							HttpStatus.FOUND.name(),
							HttpStatus.FOUND, 
							appointmentService.findByHospEmail(email)
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
	
	@GetMapping("/deleteByHospital/{email}")
	public ResponseEntity<Object> deleteByHospital(@PathVariable String email){
		try {
			return 
				APIResponse.
					generateResponse(
							"deleted sucessfully",
							HttpStatus.OK, 
							appointmentService.deleteByHospital(email)
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
	
	@GetMapping("/getByHospAndEmp/{email}")
	public ResponseEntity<Object> findByHospAndEmp(@RequestParam("empEmail") String empEmail,
			                                       @RequestParam("HospEmail") String hospEmail){
		try {
			return 
				APIResponse.
					generateResponse(
							HttpStatus.FOUND.name(),
							HttpStatus.FOUND, 
							appointmentService.findByHospAndEmp(empEmail,hospEmail)
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
	
	@GetMapping("/deleteByHospAndEmp/{email}")
	public ResponseEntity<Object> deleteByHospAndEmp(@RequestParam("empEmail") String empEmail,
			                                       @RequestParam("HospEmail") String hospEmail){
		try {
			return 
				APIResponse.
					generateResponse(
							"Deleted Sucessfully",
							HttpStatus.OK, 
							appointmentService.deleteByHospAndEmp(empEmail,hospEmail)
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
	

