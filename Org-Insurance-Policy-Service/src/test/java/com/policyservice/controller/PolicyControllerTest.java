package com.policyservice.controller;

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
import com.policyservice.entity.Policy;
import com.policyservice.exceptions.CustomExceptions;
import com.policyservice.service.PolicyService;

@SpringBootTest
public class PolicyControllerTest {

	  private MockMvc mockMvc;
//    @Autowired
//	private WebApplicationContext context;
    
    @InjectMocks
    private PolicyRestController restController;
    
    @Autowired
    ObjectMapper mapper;
   
    @Mock
    private PolicyService service;

    private Policy policy;

    @BeforeEach
    public void initialize() {
    	 mockMvc = MockMvcBuilders.standaloneSetup(restController).build();
//    	mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    	 policy = new Policy("o1@o1.com", "name1");
    }
    @AfterEach
    public void deallocate() {
    	policy = null;
    }

    @Test
    public void CreatePolicynWhenPolicyNotExist() throws Exception {
    	when(service.createPolicy(any())).thenReturn(policy);
        String json = mapper.writeValueAsString(policy);

        		mockMvc.perform(post("/policy/api/create-policy")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.HttpStatus", is(201)))
                .andExpect(jsonPath("$.data.orgEmail", is("o1@o1.com")))
                .andExpect(jsonPath("$.data.policyName", is("name1")))
                .andReturn();

    }
    
    @Test
    public void CreateEmployeeWithConflictWhenEmployeeAlreadyExistAndThrowCustomException() throws Exception {
        when(service.createPolicy(any())).
        thenThrow(new CustomExceptions(
        		"Policy Already Exist with given name : "+policy.getPolicyName()));
        String json = mapper.writeValueAsString(policy);
        mockMvc.perform(post("/policy/api/create-policy")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.HttpStatus", is(400)))
                .andExpect(jsonPath("$.message", is("Policy Already Exist with given name : "+policy.getPolicyName())))
                .andReturn();
    }

}
