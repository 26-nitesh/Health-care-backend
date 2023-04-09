package com.service.employee;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.empservice.employee.entity.Employee;
import com.empservice.employee.repo.EmployeeRepository;
import com.empservice.employee.service.EmployeeService;

@SpringBootTest
public class EmployeeServiceTest {

	private Employee employee = null;
	
	@Autowired private EmployeeService empService;
	
	@MockBean private EmployeeRepository empRepo;
	
	@BeforeEach
	public void SetUp() {
		employee  = new Employee();
		employee.setEmpEmail("emp@emp.com");
		employee.setPassword("pass");
		employee.setOrgEmail("org@org.com");
	}
	
	@AfterEach
	public void tthrow() {
		employee = null;
	}
}
