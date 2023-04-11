package com.agencyservice.agency.controller;

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
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.agencyservice.agency.entity.Agency;
import com.agencyservice.agency.exceptions.CustomExceptions;
import com.agencyservice.agency.services.AgencyService;
import com.fasterxml.jackson.databind.ObjectMapper;


@SpringBootTest
public class AgencyRestControllerTest {

    private MockMvc mockMvc;
//    @Autowired
//	private WebApplicationContext context;
    
    @InjectMocks
    private AgencyRestController restController;
    
    @Autowired
    ObjectMapper mapper;
   
    @Mock
    private AgencyService agencyService;

    private Agency agency;

    @BeforeEach
    public void initialize() {
    	 mockMvc = MockMvcBuilders.standaloneSetup(restController).build();
//    	mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    	 agency = new Agency("agency@agency.com", "agency", "pass1");
    }
    @AfterEach
    public void deallocate() {
    	agency = null;
    }

    @Test
    public void CreateAgencyWhenAgencyNotExist() throws Exception {
        when(agencyService.createNewAgency(any())).thenReturn(agency);
        String orgJson = mapper.writeValueAsString(agency);
//        MvcResult andReturn = 
        		mockMvc.perform(post("/api/agency/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(orgJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.HttpStatus", is(201)))
                .andExpect(jsonPath("$.data.agencyName", is("agency")))
                .andExpect(jsonPath("$.data.agencyEmail", is("agency@agency.com"))).andReturn();

    }

    @Test
    public void CreateAgencyWithConflictWhenOrganisationAlreadyExistAndThrowCustomException() throws Exception {
        when(agencyService.createNewAgency(any())).
        thenThrow(new CustomExceptions(
        		"Agency Already Exist with the email :", "agency@agency.com"));
        String orgJson = mapper.writeValueAsString(agency);
        mockMvc.perform(post("/api/agency/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(orgJson))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.HttpStatus", is(409)))
                .andExpect(jsonPath("$.message", is("Agency Already Exist with the email : agency@agency.com")))
                .andReturn();
    }
    
}
