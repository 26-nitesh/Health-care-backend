package com.empservice.employee.service;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.empservice.employee.entity.Employee;
import com.empservice.employee.exceptions.CustomExceptions;
import com.empservice.employee.exceptions.ResourceNotFoundException;
import com.empservice.employee.repo.EmployeeRepository;
import com.empservice.employee.service.EmployeeService;
import com.empservice.employee.utils.EmployeeDto;

@SpringBootTest
public class EmployeeServiceTest {

	private Employee employee = null;
	private EmployeeDto dto = null;
	
	@Autowired ModelMapper mapper;
	
	@Autowired private EmployeeService empService;
	
	@MockBean private EmployeeRepository empRepo;
	
	@BeforeEach
	public void SetUp() {
	    dto = new EmployeeDto();
	    dto.setEmpEmail("emp@emp.com");
	    dto.setPassword("pass");
	    dto.setOrgEmail("org@org.com");
	    
	}
	
	@AfterEach
	public void drop() {
		employee = null;
		dto = null;
	}
	
	@Test
	public void createEmp_withNewEmp_shouldReturnEmp() throws CustomExceptions, ResourceNotFoundException {

		

	}
}
