package com.service.hospital.service.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.hospital.controller.HospitalRestController;
import com.service.hospital.entity.Hospital;
import com.service.hospital.exceptions.CustomExceptions;
import com.service.hospital.service.HospitalService;

@SpringBootTest
public class HospitalRestControllerTest {

	private MockMvc mockMvc;
//  @Autowired
//	private WebApplicationContext context;
  
  @InjectMocks
  private HospitalRestController restController;
  
  @Autowired
  ObjectMapper mapper;
 
  @Mock
  private HospitalService hospitalService;

  private Hospital hospital;

  @BeforeEach
  public void initialize() {
  	 mockMvc = MockMvcBuilders.standaloneSetup(restController).build();
//  	mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
  	hospital = new Hospital("hosp@hosp.com", "pass1", "agency@agency.com");
  }
  @AfterEach
  public void deallocate() {
	  hospital = null;
  }

  @Test
  public void CreateHospitalWhenHospitalNotExist() throws Exception {
      when(hospitalService.createHospital(any())).thenReturn(hospital);
      String orgJson = mapper.writeValueAsString(hospital);
//      MvcResult andReturn = 
      		mockMvc.perform(post("/api/hospital/add-hospital")
              .contentType(MediaType.APPLICATION_JSON)
              .content(orgJson))
              .andExpect(status().isCreated())
              .andExpect(jsonPath("$.HttpStatus", is(201)))
              .andExpect(jsonPath("$.data.hospitalEmail", is("hosp@hosp.com")))
              .andExpect(jsonPath("$.data.agencyEmail", is("agency@agency.com"))).andReturn();

  }

  @Test
  public void CreateHospitalWithConflictWhenHospitalAlreadyExistAndThrowCustomException() throws Exception {
      when(hospitalService.createHospital(any())).
      thenThrow(new CustomExceptions(
      		"Hospital Already Exist with the email :", "hosp@hosp.com"));
      String orgJson = mapper.writeValueAsString(hospital);
      mockMvc.perform(post("/api/hospital/add-hospital")
              .contentType(MediaType.APPLICATION_JSON)
              .content(orgJson))
              .andExpect(status().isConflict())
              .andExpect(jsonPath("$.HttpStatus", is(409)))
              .andExpect(jsonPath("$.message", is("Hospital Already Exist with the email : hosp@hosp.com")))
              .andReturn();
  }
}
