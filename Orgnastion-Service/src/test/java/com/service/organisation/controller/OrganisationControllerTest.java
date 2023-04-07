package com.service.organisation.controller;

import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.organisation.entity.Organisation;
import com.service.organisation.exceptions.CustomExceptions;
import com.service.organisation.service.OrganisationServiceTest;
import com.service.organisation.services.OrgService;

@WebMvcTest(Organisation.class)
//@SpringBootTest
public class OrganisationControllerTest {

	   @Autowired
	    private MockMvc mockMvc;

	   @Autowired ObjectMapper mapper;
	   
	    @MockBean
	    private OrgService orgServices;

	    private Organisation org1;

	    @BeforeEach
	    public void setup() {
	        org1 = new Organisation("agency@agency.com", "org1", "org1@org.com", "pass1");
	    }

	    @Test
	    public void testCreateOrganisation() throws Exception {
	        when(orgServices.createOrg((Organisation) any(Organisation.class))).thenReturn(org1);
	        String orgJson = mapper.writeValueAsString(org1);
	        mockMvc.perform(post("/addOrg")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(orgJson))
	                .andExpect(status().isCreated())
	                .andExpect(jsonPath("$.status", is("CREATED")))
	                .andExpect(jsonPath("$.data.name", is("org1")))
	                .andExpect(jsonPath("$.data.email", is("org1@org.com")));
	    }

	    @Test
	    public void testCreateOrganisationConflict() throws Exception {
	        when(orgServices.createOrg((Organisation) any(Organisation.class))).
	        thenThrow(new CustomExceptions(
	        		"Organisation Already Exist with the email : ", "org1@org.com"));
	        String orgJson = mapper.writeValueAsString(org1);
	        mockMvc.perform(post("/api/organisation/addOrg")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(orgJson))
	                .andExpect(status().isConflict())
	                .andExpect(jsonPath("$.status", is("CONFLICT")))
	                .andExpect(jsonPath("$.message", is("Organisation Already Exist with the email : org1@org.com")))
	                .andExpect(jsonPath("$.data", is(nullValue())));
	    }
	
}
