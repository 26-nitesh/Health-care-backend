package com.service.appointment.controllers;

import java.time.LocalDate;
import java.util.Date;

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

import com.service.appointment.entity.AppointMent;
import com.service.appointment.exceptions.CustomExceptions;
import com.service.appointment.exceptions.ResourceNotFoundException;
import com.service.appointment.service.AppointmentService;
import com.service.appointment.utils.APIResponse;
import com.service.appointment.utils.UpdateAppointmentO;


@RestController
@RequestMapping("/appointment/api")
@CrossOrigin("*")
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
							HttpStatus.OK, 
							appointmentService.findByEmpEmail(email)
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
	public ResponseEntity<Object> findByHosp(@PathVariable String email,@RequestParam(value = "archived") boolean archived ){
		try {
			return 
				APIResponse.
					generateResponse(
							HttpStatus.FOUND.name(),
							HttpStatus.OK, 
							appointmentService.findByHospEmail(email,archived)
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
	
	@GetMapping("/findAllByHosp/{email}")
	public ResponseEntity<Object> findByHospital(@PathVariable String email){
		try {
			return 
				APIResponse.
					generateResponse(
							HttpStatus.FOUND.name(),
							HttpStatus.OK, 
							appointmentService.findAllByHospEmail(email)
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
	
	@GetMapping("/getByHospAndEmp")
	public ResponseEntity<Object> findByHospAndEmp(@RequestParam(value = "empEmail",required = true) String empEmail,
			                                       @RequestParam(value = "HospEmail",required = true) String hospEmail){
		try {
			return 
				APIResponse.
					generateResponse(
							HttpStatus.FOUND.name(),
							HttpStatus.FOUND, 
							appointmentService.findByHospAndEmp(empEmail,hospEmail)
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
	
	@PutMapping("/updateAppointmnet")
	public ResponseEntity<Object> updateAppointment(@RequestBody AppointMent appointment){
		try {
			return 
				APIResponse.
					generateResponse(
							"Updated succesfully",
							HttpStatus.OK, 
							appointmentService.updateAppintment(appointment)
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
	
	@GetMapping("/appointMent")
	public ResponseEntity<Object> findByEmailAndDate(@RequestParam(value = "email",required = true) String empEmail,
			                                          @RequestParam(value = "date",required = true) LocalDate date){
		try {
			return 
				APIResponse.
					generateResponse(
							HttpStatus.FOUND.name(),
							HttpStatus.OK, 
							appointmentService.findByEmpAndDate(empEmail,date)
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
	@GetMapping("/updateAppointmnet/{id}")
	public ResponseEntity<Object> updateStatus(@PathVariable int id,
			                                  @RequestParam(required = true,name = "status") String status,
			                                  @RequestParam (required = false,name="amount")Double amount,
			                                  @RequestParam(required = false,name="claimRemarks") String claimRemarks){
		try {
			return 
				APIResponse.
					generateResponse(
							"Updated succesfully",
							HttpStatus.OK, 
							appointmentService.updateStatus(id,status,amount,claimRemarks)
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
	
	@GetMapping("/")
	public ResponseEntity<Object> findAll(){
		try {
			return 
				APIResponse.
					generateResponse(
							HttpStatus.OK.name(),
							HttpStatus.OK, 
							appointmentService.findAllApps()
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
	
//	@GetMapping("/updateAppointmnet/{id}")
//	public ResponseEntity<Object> updateAppointmentById(@RequestBody AppointMent appointment){
//		try {
//			return 
//				APIResponse.
//					generateResponse(
//							"Updated succesfully",
//							HttpStatus.OK, 
//							appointmentService.updateAppintment(appointment)
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
	

