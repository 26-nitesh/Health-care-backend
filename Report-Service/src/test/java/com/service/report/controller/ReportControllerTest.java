package com.service.report.controller;

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
import com.service.report.entity.Report;
import com.service.report.exceptions.CustomExceptions;
import com.service.report.service.ReportService;


@SpringBootTest
public class ReportControllerTest {

	 private MockMvc mockMvc;
//   @Autowired
//	private WebApplicationContext context;
   
   @InjectMocks
   private ReportRestController restController;
   
   @Autowired
   ObjectMapper mapper;
  
   @Mock
   private ReportService service;

   private Report report;

   @BeforeEach
   public void initialize() {
   	 mockMvc = MockMvcBuilders.standaloneSetup(restController).build();
//   	mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
   	 report = new Report();
   	 report.setAppointmentId(12);
   	 report.setReportDetails("details");
   }
   @AfterEach
   public void deallocate() {
   	report = null;
   }

//   @Test
//   public void CreateReportWhenReportNotExist() throws Exception {
//   	when(service.createReport(any())).thenReturn(report);
//       String json = mapper.writeValueAsString(report);
//
//       		mockMvc.perform(post("/report/api/create-report")
//               .contentType(MediaType.APPLICATION_JSON)
//               .content(json))
//               .andExpect(status().isCreated())
//               .andExpect(jsonPath("$.HttpStatus", is(201)))
//               .andExpect(jsonPath("$.data.appointmentId", is(12)))
//               .andReturn();
//
//   }
   
//   @Test
//   public void CreateReportWhenReporExist_throwExcp() throws Exception {
//   	when(service.createReport(any())).thenThrow(new CustomExceptions("Report already Exist for given appointment "));
//       String json = mapper.writeValueAsString(report);
//
//       		mockMvc.perform(post("/report/api/create-report")
//               .contentType(MediaType.APPLICATION_JSON)
//               .content(json))
//               .andExpect(status().isConflict())
//               .andExpect(jsonPath("$.HttpStatus", is(409)))
//               .andReturn();
//
//   }
   
}
