package com.service.report.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;


import com.service.report.entity.Report;
import com.service.report.exceptions.CustomExceptions;
import com.service.report.exceptions.ResourceNotFoundException;
import com.service.report.repo.ReportRepo;



@SpringBootTest
public class ReportServiceTest {

//	@InjectMocks
	@Autowired
	private ReportService reportService;

	@MockBean
	private ReportRepo reportRepo;

	Report report = null;



	@BeforeEach
	public void getOrg() {
		  MockitoAnnotations.openMocks(this);
		report = new Report();
		report.setAppointmentId(1);
		report.setReportId(787);
		report.setRemarks("PAsSS");
	}

	@AfterEach
	public void disposeOrg() {
	report = null;
	}
	@Test
	public void createRecordwithNew_Roprt() throws CustomExceptions, ResourceNotFoundException {

		when(reportRepo.findByAppointmentId(1)).thenReturn(Optional.empty());
		when(reportRepo.save(report)).thenReturn(report);

		// Act
		 Report result = reportService.createReport(report);

		// Assert
		verify(reportRepo, times(1)).findByAppointmentId(1);
		verify(reportRepo, times(1)).save(report);
		assertNotNull(result);
		assertEquals(report, result);

	}
	
	@Test
	public void createRecordwithExisting_RoprtThrowExp() throws CustomExceptions, ResourceNotFoundException {

		when(reportRepo.findByAppointmentId(1)).thenReturn(Optional.of(report));
		CustomExceptions exception = assertThrows(CustomExceptions.class, () -> {
			reportService.createReport(report);
		});

		// Act

		// Assert
		verify(reportRepo, times(1)).findByAppointmentId(1);
		assertEquals(exception.getMessage(), "Report already Exist for given appointment ");

	}
	
	@Test
	public void findByAppId_if_found_Return() throws  ResourceNotFoundException {

		when(reportRepo.findByAppointmentId(1)).thenReturn(Optional.of(report));
		

		// Act
              Report findByAppId = reportService.findByAppId(1);
		// Assert
		verify(reportRepo, times(1)).findByAppointmentId(1);
		assertNotNull(findByAppId);
		assertEquals(findByAppId, report);
		
	}
	
	@Test
	public void findByAppId_if_NOT_found_ResourceNotFoundException() throws  ResourceNotFoundException {

//		when(reportRepo.findByAppointmentId(1)).thenReturn(Optional.ofNullable(null));
//		when(reportRepo.findByAppointmentId(1)).thenReturn(Optional.of(report));
		ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
			 reportService.findByAppId(1);
		});

		// Act
//              Report findByAppId = reportService.findByAppId(1);
		// Assert
		verify(reportRepo, times(1)).findByAppointmentId(1);
//		assertNotNull(findByAppId);
		assertEquals(exception.getMessage(), "Report not found with Appointment Id : 1");
		
	}
}
