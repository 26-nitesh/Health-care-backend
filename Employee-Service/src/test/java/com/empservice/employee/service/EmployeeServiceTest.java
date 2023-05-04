package com.empservice.employee.service;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
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
	
	
	@Autowired private EmployeeService empService;
	
	@MockBean private EmployeeRepository empRepo;
	
	@BeforeEach
	public void SetUp() {
		employee = new Employee();
		employee.setEmpEmail("emp@emp.com");
		employee.setPassword("pass");
		employee.setOrgEmail("org@org.com");
	    
	}
	
	@AfterEach
	public void drop() {
		employee = null;
	}
	
	@Test
	public void createEmp_withNewEmp_shouldReturnEmp() throws CustomExceptions, ResourceNotFoundException {

		when(empRepo.findByEmpEmailAndOrgEmail(employee.getEmpEmail(),employee.getOrgEmail())).thenReturn(Optional.ofNullable(null));
		when(empRepo.save(employee)).thenReturn(employee);

		// Act
		  Employee result = empService.createEmployee(employee);

		// Assert
		verify(empRepo, times(1)).findByEmpEmailAndOrgEmail(employee.getEmpEmail(),employee.getOrgEmail());
		verify(empRepo, times(1)).save(employee);
		assertNotNull(result);
		assertEquals(employee, result);

	}
	
	@Test
	public void createEmp_withExistingEmp_shouldThrowCustomExceptions() {

		when(empRepo.findByEmpEmailAndOrgEmail(employee.getEmpEmail(),employee.getOrgEmail())).thenReturn(Optional.of(employee));

		// Act and Assert
		CustomExceptions exception = assertThrows(CustomExceptions.class, () -> {
			empService.createEmployee(employee);
		});

		// Assert
		verify(empRepo, times(1)).findByEmpEmailAndOrgEmail(employee.getEmpEmail(),employee.getOrgEmail());
		verify(empRepo, never()).save(employee);
		assertEquals("Employee Already Exist with the email :  emp@emp.com", exception.getMessage());
	}
	
	@Test
	public void getByEmail_when_NoEmailPresent_shouldThrowResoureNotFoundException() throws ResourceNotFoundException {

		when(empRepo.findByEmpEmail(employee.getEmpEmail())).thenReturn(Optional.ofNullable(null));

		ResourceNotFoundException exp = assertThrows(ResourceNotFoundException.class,
				() -> empService.findEmpByEmail("emp@emp.com"));
		verify(empRepo, times(1)).findByEmpEmail(employee.getEmpEmail());
		assertEquals("Employee not found with email : emp@emp.com", exp.getMessage());
	}

	@Test
	public void getByEmail_when_EmailPresent_should_ReturnEmployee() throws ResourceNotFoundException {

		when(empRepo.findByEmpEmail(employee.getEmpEmail())).thenReturn(Optional.of(employee));

		Employee result = empService.findEmpByEmail("emp@emp.com");
		verify(empRepo, times(1)).findByEmpEmail(employee.getEmpEmail());
		assertNotNull(result);
		assertEquals(employee, result);
	}
	
	@Test
	public void deleteByEmail_when_NoEmailPresent_shouldThrowResoureNotFoundException()
			throws ResourceNotFoundException {

		when(empRepo.findByEmpEmail(employee.getEmpEmail())).thenReturn(Optional.ofNullable(null));

		ResourceNotFoundException exp = assertThrows(ResourceNotFoundException.class,
				() -> empService.deleteEmpByEmail("emp@emp.com"));
		verify(empRepo, times(1)).findByEmpEmail(employee.getEmpEmail());
		assertEquals("employee not found with email : emp@emp.com", exp.getMessage());
	}

	@Test
	public void getByEmail_when_EmailPresent_should_deleteOrganisation() throws ResourceNotFoundException, CustomExceptions {

		when(empRepo.findByEmpEmail(employee.getEmpEmail())).thenReturn(Optional.of(employee));

		Employee result = empService.deleteEmpByEmail("emp@emp.com");
		verify(empRepo, times(1)).findByEmpEmail(employee.getEmpEmail());
		assertNotNull(result);
		assertEquals(employee, result);
	}
}
