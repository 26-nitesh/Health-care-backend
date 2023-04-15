package com.service.appointment.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

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

import com.service.appointment.controllers.AppointMentRestController;
import com.service.appointment.entity.AppointMent;
import com.service.appointment.exceptions.CustomExceptions;
import com.service.appointment.service.AppointmentService;

@SpringBootTest
public class AppointmentControllerTest {

	  private MockMvc mockMvc;
//    @Autowired
//	private WebApplicationContext context;
    
    @InjectMocks
    private AppointMentRestController restController;
    
    @Autowired
    ObjectMapper mapper;
   
    @Mock
    private AppointmentService service;

    private AppointMent app;

    @BeforeEach
    public void initialize() {
    	 mockMvc = MockMvcBuilders.standaloneSetup(restController).build();
//    	mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    	 app = new AppointMent();
    	 app.setEmployeeEmail("e@e");
    	 app.setAppointmentDate(LocalDate.of(2022, 12, 12));
    }
    @AfterEach
    public void deallocate() {
    	app = null;
    }

    @Test
    public void CreateAppointMentWhenAppointMentNotExist() throws Exception {
    	when(service.createnewAppointMent(any())).thenReturn(app);
        String json = mapper.writeValueAsString(app);

        		mockMvc.perform(post("/appointment/api/create-new-appointment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.HttpStatus", is(201)))
                .andExpect(jsonPath("$.data.employeeEmail", is("e@e")))
//                .andExpect(jsonPath("$.data.policyName", is("name1")))
                .andReturn();

    }
    
    @Test
    public void CreateAppointMentWithConflictWhenAppointMentAlreadyExistAndThrowCustomException() throws Exception {
        when(service.createnewAppointMent(any())).
        thenThrow(new CustomExceptions(
        		"Appointment already Exist",""));
        String json = mapper.writeValueAsString(app);
        mockMvc.perform(post("/appointment/api/create-new-appointment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.HttpStatus", is(409)))
//                .andExpect(jsonPath("$.message", is("Appointment already Exist")))
                .andReturn();
    }
}
