package com.service.organisation.controller;

import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.any;
import java.util.Optional;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.organisation.entity.Organisation;
import com.service.organisation.exceptions.CustomExceptions;
import com.service.organisation.exceptions.ResourceNotFoundException;
import com.service.organisation.service.OrganisationServiceTest;
import com.service.organisation.services.OrgService;

@WebMvcTest(Organisation.class)@ExtendWith(MockitoExtension.class)
//@SpringBootTest
public class OrganisationControllerTest {

	    private MockMvc mockMvc;
//	    @Autowired
//		private WebApplicationContext context;
	    
	    @InjectMocks
	    private OrgRestController restController;
	    
	    @Autowired
	    ObjectMapper mapper;
	   
	    @MockBean
	    private OrgService orgServices;

	    private Organisation org1;

	    @BeforeEach
	    public void initialize() {
	    	 mockMvc = MockMvcBuilders.standaloneSetup(restController).build();
//	    	mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	        org1 = new Organisation("agency@agency.com", "org1", "org1@org.com", "pass1");
	    }
	    @AfterEach
	    public void deallocate() {
	    	org1 = null;
	    }

	    @Test
	    public void CreateOrganisationWhenOrganiasationNotExist() throws Exception {
	        when(orgServices.createOrg(any())).thenReturn(org1);
	        String orgJson = mapper.writeValueAsString(org1);
//	        MvcResult andReturn = 
	        		mockMvc.perform(post("/api/organisation/addOrg")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(orgJson))
	                .andExpect(status().isCreated())
	                .andExpect(jsonPath("$.HttpStatus", is(201)))
	                .andExpect(jsonPath("$.data.organisationName", is("org1")))
	                .andExpect(jsonPath("$.data.organisationEmail", is("org1@org.com"))).andReturn();
//	        System.out.println(andReturn.getResponse().getContentAsString());
//	        System.out.println(orgJson);
	    }

	    @Test
	    public void CreateOrganisationWithConflictWhenOrganisationAlreadyExistAndThrowCustomException() throws Exception {
	        when(orgServices.createOrg(any())).
	        thenThrow(new CustomExceptions(
	        		"Organisation Already Exist with the email :", "org1@org.com"));
	        String orgJson = mapper.writeValueAsString(org1);
	        mockMvc.perform(post("/api/organisation/addOrg")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(orgJson))
	                .andExpect(status().isConflict())
	                .andExpect(jsonPath("$.HttpStatus", is(409)))
	                .andExpect(jsonPath("$.message", is("Organisation Already Exist with the email : org1@org.com")))
	                .andReturn();
	    }
	    
	    @Test
	    public void getOrganisationWhenNotPresentThrowResourceNotFoundException() throws Exception {
	        when(orgServices.getByEmail("s@s.com")).
	        thenThrow(new ResourceNotFoundException(
	        		"Organisation", "email" ,"s@s.com"));
	        mockMvc.perform(get("/api/organisation/org/s@s.com"))
	                .andExpect(status().isNotFound())
	                .andExpect(jsonPath("$.HttpStatus", is(404)))
	                .andExpect(jsonPath("$.message", is("Organisation not found with email : s@s.com")))
	                .andReturn();
	    }
	
	    @Test
	    public void getOrganisationWhenPresentReturnOrganisation() throws Exception {
	        when(orgServices.getByEmail("org1@org.com")).
	        thenReturn(org1);
	        mockMvc.perform(get("/api/organisation/org/org1@org.com"))
	                .andExpect(status().isOk())
	                .andExpect(jsonPath("$.HttpStatus", is(200)))
	                .andExpect(jsonPath("$.data.organisationName", is(org1.getOrganisationName())))
	                .andReturn();
	    }
}
