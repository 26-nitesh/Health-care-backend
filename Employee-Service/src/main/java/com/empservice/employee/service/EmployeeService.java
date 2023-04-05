package com.empservice.employee.service;

import java.util.List;

import javax.persistence.Entity;

import com.empservice.employee.utils.Address;
import com.empservice.employee.utils.EmployeeDto;
import com.empservice.employee.utils.User;

public interface EmployeeService {

	EmployeeDto createEmployee(EmployeeDto empUtil);

	EmployeeDto findEmpByEmail(String email);

	List<EmployeeDto> findEmpsByOrgEmail(String email);

	EmployeeDto deleteEmpByEmail(String email);

	EmployeeDto updateOrgEmailByEmpEmail(String empEmail,String orgEmail);

	EmployeeDto updateAddressEmpByEmail(String email,Address address);

	String getPasswordByEmail(String email);

	EmployeeDto validateEmployee(User user);

}
