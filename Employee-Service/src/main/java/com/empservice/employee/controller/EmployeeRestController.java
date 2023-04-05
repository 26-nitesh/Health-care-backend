package com.empservice.employee.controller;

import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.empservice.employee.exceptions.CustomExceptions;
import com.empservice.employee.exceptions.ResourceNotFoundException;
import com.empservice.employee.service.EmployeeService;
import com.empservice.employee.utils.APIResponse;
import com.empservice.employee.utils.Address;
import com.empservice.employee.utils.EmployeeDto;
import com.empservice.employee.utils.User;


@RestController
@RequestMapping("/api/employee")
public class EmployeeRestController {
	
	@Autowired EmployeeService empService;
	
	@PostMapping("/create-new-employee")
	public ResponseEntity<Object> createNewEmployee(@RequestBody EmployeeDto empDto){
		try {
			return 
				APIResponse.
					generateResponse(
							HttpStatus.CREATED.name(),
							HttpStatus.CREATED, 
							empService.createEmployee(empDto)
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
	
	@GetMapping("/{email}")
	public ResponseEntity<Object> findByEmail(@PathVariable String email){
		try {
			return 
					APIResponse.generateResponse(
							HttpStatus.FOUND.name(),
							HttpStatus.FOUND,
							empService.findEmpByEmail(email)
							);
		} catch (ResourceNotFoundException e) {
			return 
					APIResponse.generateResponse(
							e.getMessage(),
							HttpStatus.NOT_FOUND,
							null);
		}
		
	}
	
	@GetMapping("org/{email}") //getAllEmployeesByORgEmail
	public ResponseEntity<Object> getEmployeesByOrgEmail(@PathVariable String email){
		try {
			return 
					APIResponse.generateResponse(
							HttpStatus.FOUND.name(),
							HttpStatus.FOUND,
							empService.findEmpsByOrgEmail(email)
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
							empService.deleteEmpByEmail(email)
							);
		} catch (ResourceNotFoundException e) {
			return 
					APIResponse.generateResponse(
							e.getMessage(),
							HttpStatus.NOT_FOUND,
							null);
		}
		
	}
	@PutMapping("/update-orgEmail/")
	public ResponseEntity<Object> updateOrgEmailByEmpEmail(
			                                              @RequestParam(value = "empEmail",required = true) String empEmail,
			                                               @RequestParam(value = "orgEmail",required = true) String orgEmail){
		try {
			return 
					APIResponse.generateResponse(
							"updated sucessfully",
							HttpStatus.OK,
							empService.updateOrgEmailByEmpEmail(empEmail,orgEmail)
							);
		} catch (ResourceNotFoundException e) {
			return 
					APIResponse.generateResponse(
							e.getMessage(),
							HttpStatus.NOT_FOUND,
							null);
		}
		
	}
	@PutMapping("/updateAddress/{email}")
	public ResponseEntity<Object> updateOrgEmailByEmpEmail(@PathVariable String email,@RequestBody Address address){
		try {
			return 
					APIResponse.generateResponse(
							"updated sucessfully",
							HttpStatus.OK,
							empService.updateAddressEmpByEmail(email,address)
							);
		} catch (ResourceNotFoundException e) {
			return 
					APIResponse.generateResponse(
							e.getMessage(),
							HttpStatus.NOT_FOUND,
							null);
		}
		
	}

//	@GetMapping("/getPassword/{email}")
//	public String getPassword(@PathVariable String email) {
//		return empService.getPasswordByEmail(email);
//		
//		}
	@PostMapping("/validate-emp")
	public ResponseEntity<Object>  validateEmp(@RequestBody User user){
		try {
			EmployeeDto emp = empService.validateEmployee(user);
			return APIResponse.generateResponse(HttpStatus.OK.name(), HttpStatus.OK, Map.of("User Status", "Valid", "Email", emp.getEmpEmail(),"Organisation Email",emp.getOrgEmail()));
		}catch (CustomExceptions e) {
			return APIResponse.generateResponse(e.getMessage(), HttpStatus.FORBIDDEN, Map.of("User Status","Not Valid","Email",user.getEmail()));
		}
	}

}
