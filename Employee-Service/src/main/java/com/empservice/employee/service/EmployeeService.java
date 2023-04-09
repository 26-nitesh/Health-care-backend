package com.empservice.employee.service;

import java.util.List;

import javax.persistence.Entity;

import com.empservice.employee.entity.Employee;
import com.empservice.employee.exceptions.CustomExceptions;
import com.empservice.employee.exceptions.ResourceNotFoundException;
import com.empservice.employee.utils.Address;
import com.empservice.employee.utils.EmployeeDto;
import com.empservice.employee.utils.User;

public interface EmployeeService {

	EmployeeDto createEmployee(EmployeeDto empUtil) throws CustomExceptions, ResourceNotFoundException;

	EmployeeDto findEmpByEmail(String email) throws ResourceNotFoundException;

	List<EmployeeDto> findEmpsByOrgEmail(String email) throws ResourceNotFoundException;

	EmployeeDto deleteEmpByEmail(String email) throws ResourceNotFoundException, CustomExceptions;

	EmployeeDto updateOrgEmailByEmpEmail(String empEmail,String orgEmail) throws CustomExceptions, ResourceNotFoundException;

	EmployeeDto updateAddressEmpByEmail(String email,Address address) throws ResourceNotFoundException, CustomExceptions;

	Employee updateEmployee(Employee employee) throws CustomExceptions,ResourceNotFoundException;

	Object validateUserAndGetToken(User user) throws ResourceNotFoundException;

	Employee changePassword(User user) throws ResourceNotFoundException, CustomExceptions;
	EmployeeDto ModelToDto(Employee employee);
	 Employee dtoToModel(EmployeeDto empDto);
//	String getPasswordByEmail(String email);
//
//	EmployeeDto validateEmployee(User user);

}
