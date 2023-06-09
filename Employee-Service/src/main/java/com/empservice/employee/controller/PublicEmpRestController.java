//package com.empservice.employee.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.empservice.employee.exceptions.CustomExceptions;
//import com.empservice.employee.exceptions.ResourceNotFoundException;
//import com.empservice.employee.service.EmployeeService;
//import com.empservice.employee.utils.APIResponse;
//import com.empservice.employee.utils.EmployeeDto;
//
//@RestController
//@RequestMapping("/public")
//public class PublicEmpRestController {
//
//@Autowired EmployeeService empService;
//	
//	@PostMapping("/create-new-employee")
//	public ResponseEntity<Object> createNewEmployee(@RequestBody EmployeeDto empDto){
//		try {
//			return 
//				APIResponse.
//					generateResponse(
//							HttpStatus.CREATED.name(),
//							HttpStatus.CREATED, 
//							empService.createEmployee(empDto)
//							);
//		} catch (CustomExceptions  | ResourceNotFoundException e) {
//			return 
//				APIResponse.
//				generateResponse(
//					e.getMessage(),
//					HttpStatus.CONFLICT,
//					null
//					);
//		}
//		
//	}
//}
