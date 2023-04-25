package com.empservice.employee.service;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;

import org.springframework.stereotype.Service;

import com.empservice.employee.entity.Employee;
import com.empservice.employee.exceptions.CustomExceptions;
import com.empservice.employee.exceptions.ResourceNotFoundException;
import com.empservice.employee.utils.Address;
import com.empservice.employee.utils.EmployeeDto;
import com.empservice.employee.utils.User;

@Service
public interface EmployeeService {

	Employee createEmployee(Employee employee) throws CustomExceptions, ResourceNotFoundException;

	Employee findEmpByEmail(String email) throws ResourceNotFoundException;

	List<Employee> findEmpsByOrgEmail(String email) throws ResourceNotFoundException;

	Employee deleteEmpByEmail(String email) throws ResourceNotFoundException, CustomExceptions;

	Employee updateOrgEmailByEmpEmail(String empEmail,String orgEmail) throws CustomExceptions, ResourceNotFoundException;

	Employee updateAddressEmpByEmail(String email,Address address) throws ResourceNotFoundException, CustomExceptions;

	Employee updateEmployee(Employee employee) throws CustomExceptions,ResourceNotFoundException;

	Object validateUserAndGetToken(User user) throws ResourceNotFoundException;

	Employee changePassword(User user) throws ResourceNotFoundException, CustomExceptions;

	Object updateLastAppDateEmpEmail(String email, LocalDate lastAppDate) throws ResourceNotFoundException, CustomExceptions;;
	
}
