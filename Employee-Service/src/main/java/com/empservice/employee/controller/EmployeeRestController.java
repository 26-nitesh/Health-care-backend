package com.empservice.employee.controller;

import java.time.LocalDate;
import java.util.Map;
import org.springframework.format.annotation.DateTimeFormat;
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

import com.empservice.employee.entity.Employee;
import com.empservice.employee.exceptions.CustomExceptions;
import com.empservice.employee.exceptions.ResourceNotFoundException;
import com.empservice.employee.service.EmployeeService;
import com.empservice.employee.utils.APIResponse;
import com.empservice.employee.utils.Address;
import com.empservice.employee.utils.EmpServiceLogger;
import com.empservice.employee.utils.EmployeeDto;
import com.empservice.employee.utils.User;



@RestController
@RequestMapping("/employee/api")
@CrossOrigin
public class EmployeeRestController {
	
	@Autowired EmployeeService empService;
	
	@PostMapping("/login")
	public ResponseEntity<Object> validateUserAndGenerateToken(@RequestBody User user){
		try {
			return 
				APIResponse.
					generateResponse(
							"Sucess",
							HttpStatus.OK, 
							empService.validateUserAndGetToken(user)
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
	
	@PostMapping("/create-new-employee")
	public ResponseEntity<Object> createNewEmployee(@RequestBody Employee employee){
		try {
			EmpServiceLogger.log.info("trying to craete employee");
			return 
				APIResponse.
					generateResponse(
							HttpStatus.CREATED.name(),
							HttpStatus.CREATED, 
							empService.createEmployee(employee)
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
							HttpStatus.OK,
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
//	@PutMapping("/update-orgEmail/")
//	public ResponseEntity<Object> updateOrgEmailByEmpEmail(
//			                                              @RequestParam(value = "empEmail",required = true) String empEmail,
//			                                               @RequestParam(value = "orgEmail",required = true) String orgEmail){
//		try {
//			return 
//					APIResponse.generateResponse(
//							"updated sucessfully",
//							HttpStatus.OK,
//							empService.updateOrgEmailByEmpEmail(empEmail,orgEmail)
//							);
//		} catch (Exception e) {
//			return 
//					APIResponse.generateResponse(
//							e.getMessage(),
//							HttpStatus.BAD_REQUEST,
//							null);
//		}
//		
//	}
	@PutMapping("/updateAddress/{email}")
	public ResponseEntity<Object> updateOrgEmailByEmpEmail(@PathVariable String email,@RequestBody Address address){
		try {
			return 
					APIResponse.generateResponse(
							"updated sucessfully",
							HttpStatus.OK,
							empService.updateAddressEmpByEmail(email,address)
							);
		} catch (Exception e) {
			return 
					APIResponse.generateResponse(
							e.getMessage(),
							HttpStatus.BAD_REQUEST,
							null);
		}
		
	}

	@PutMapping("/updateLastCheckupDate/{email}")
	public ResponseEntity<Object> updateLastAppDateEmpEmail(@PathVariable String email,
			                @RequestParam(required = true,name="lastAppDate")  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate lastAppDate){
		try {
			return 
					APIResponse.generateResponse(
							"updated sucessfully",
							HttpStatus.OK,
							empService.updateLastAppDateEmpEmail(email,lastAppDate)
							);
		} catch (Exception e) {
			return 
					APIResponse.generateResponse(
							e.getMessage(),
							HttpStatus.BAD_REQUEST,
							null);
		}
		
	}
	
	@PutMapping("/update-employee")
	public ResponseEntity<Object> update(@RequestBody Employee employee){
		try {
			return 
				APIResponse.
					generateResponse(
							"updated sucessfully",
							HttpStatus.OK, 
							empService.updateEmployee(employee)
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
		
		@PutMapping("/changePassword")
		public ResponseEntity<Object> changePAssword(@RequestBody User user){
			try {
				empService.changePassword(user);
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
//	@GetMapping("/getPassword/{email}")
//	public String getPassword(@PathVariable String email) {
//		return empService.getPasswordByEmail(email);
//		
//		}
//	@PostMapping("/validate-emp")
//	public ResponseEntity<Object>  validateEmp(@RequestBody User user){
//		try {
//			EmployeeDto emp = empService.validateEmployee(user);
//			return APIResponse.generateResponse(HttpStatus.OK.name(), HttpStatus.OK, Map.of("User Status", "Valid", "Email", emp.getEmpEmail(),"Organisation Email",emp.getOrgEmail()));
//		}catch (CustomExceptions e) {
//			return APIResponse.generateResponse(e.getMessage(), HttpStatus.FORBIDDEN, Map.of("User Status","Not Valid","Email",user.getEmail()));
//		}
//	}

}
