package com.empservice.employee.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.empservice.employee.entity.Employee;
import com.empservice.employee.exceptions.CustomExceptions;
import com.empservice.employee.exceptions.ResourceNotFoundException;
import com.empservice.employee.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;


@SpringBootTest
public class EmpRestControllerTest {

    private MockMvc mockMvc;
//    @Autowired
//	private WebApplicationContext context;
    
    @InjectMocks
    private EmployeeRestController restController;
    
    @Autowired
    ObjectMapper mapper;
   
    @Mock
    private EmployeeService empServie;

    private Employee employee;

    @BeforeEach
    public void initialize() {
    	 mockMvc = MockMvcBuilders.standaloneSetup(restController).build();
//    	mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    	 employee = new Employee("emp@emp.com", "org1@org.com", "emp1", "pass1");
    }
    @AfterEach
    public void deallocate() {
//    	employee = null;
    }

    @Test
    public void CreateOrganisationWhenEMPNotExist() throws Exception {
    	when(empServie.createEmployee(any())).thenReturn(employee);
//        when(orgServices.createOrg(any())).thenReturn(org1);
        String empJson = mapper.writeValueAsString(employee);
//  any()      MvcResult andReturn = 
//        System.out.println(empJson);
        		mockMvc.perform(post("/api/employee/create-new-employee")
                .contentType(MediaType.APPLICATION_JSON)
                .content(empJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.HttpStatus", is(201)))
                .andExpect(jsonPath("$.data.empName", is("emp1")))
                .andExpect(jsonPath("$.data.empEmail", is("emp@emp.com")))
                .andReturn();
//        System.out.println(andReturn.getResponse().getContentAsString());
//        System.out.println(orgJson);
    }

    @Test
    public void CreateEmployeeWithConflictWhenEmployeeAlreadyExistAndThrowCustomException() throws Exception {
        when(empServie.createEmployee(any())).
        thenThrow(new CustomExceptions(
        		"Employee Already Exist with the email :", "emp1@emp1.com"));
        String orgJson = mapper.writeValueAsString(employee);
        mockMvc.perform(post("/api/employee/create-new-employee")
                .contentType(MediaType.APPLICATION_JSON)
                .content(orgJson))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.HttpStatus", is(409)))
                .andExpect(jsonPath("$.message", is("Employee Already Exist with the email : emp1@emp1.com")))
                .andReturn();
    }
//    
    @Test
    public void getEmployeeWhenNotPresentThrowResourceNotFoundException() throws Exception {
        when(empServie.findEmpByEmail("emp1@emp1.com")).
        thenThrow(new ResourceNotFoundException(
        		"Employee", "email" ,"emp1@emp1.com"));
        mockMvc.perform(get("/api/employee/emp1@emp1.com"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.HttpStatus", is(404)))
                .andExpect(jsonPath("$.message", is("Employee not found with email : emp1@emp1.com")))
                .andReturn();
    }
//
    @Test
    public void getOrganisationWhenPresentReturnOrganisation() throws Exception {
        when(empServie.findEmpByEmail("emp@emp.com")).
        thenReturn(employee);
        mockMvc.perform(get("/api/employee/emp@emp.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.HttpStatus", is(200)))
                .andExpect(jsonPath("$.data.empEmail", is("emp@emp.com")))
                .andReturn();
    }
}
