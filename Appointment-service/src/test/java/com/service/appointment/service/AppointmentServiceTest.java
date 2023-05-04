package com.service.appointment.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.service.appointment.entity.AppointMent;
import com.service.appointment.exceptions.CustomExceptions;
import com.service.appointment.exceptions.ResourceNotFoundException;
import com.service.appointment.repo.AppointMentRepo;



@SpringBootTest
public class AppointmentServiceTest {

	@Autowired
	private AppointmentService service;

	@MockBean
	private AppointMentRepo repo;

	AppointMent app = null;


	@BeforeEach
	public void getApp() {
		  MockitoAnnotations.openMocks(this);
		app = new AppointMent();
		app.setEmployeeEmail("a@a");
		app.setAppointmentDate(LocalDate.of(2022, 12, 12));
		app.setEmployeeEmail("e@e");
		app.setHospitalEmail("h@h");
	}

	@AfterEach
	public void disposeApp() {
	app = null;
	}
	@Test
	public void createAppointmentwithNew_Appoitnment() throws CustomExceptions, ResourceNotFoundException {

		when(repo.findByEmployeeEmailAndIsArchived(app.getEmployeeEmail(), false)).thenReturn(Optional.empty());
		when(repo.save(app)).thenReturn(app);

		// Act
		 AppointMent result = service.createnewAppointMent(app);

		// Assert
		verify(repo, times(1)).findByEmployeeEmailAndIsArchived(app.getEmployeeEmail(),false);
		verify(repo, times(1)).save(app);
		assertNotNull(result);
		assertEquals(app, result);

	}
	
	@Test
	public void createAppointmentwithExiting_Appoitnment_throw_Exp() throws CustomExceptions, ResourceNotFoundException {

		when(repo.findByEmployeeEmailAndIsArchived(app.getEmployeeEmail(), false)).thenReturn(Optional.of(app));
		CustomExceptions exception = assertThrows(CustomExceptions.class, () -> {
			service.createnewAppointMent(app);
		});


		// Assert
		verify(repo, times(1)).findByEmployeeEmailAndIsArchived(app.getEmployeeEmail(),false);
		assertEquals(exception.getMessage(), "Appointment already Exist ");

	}
	
	@Test
	public void findByHospEmailIfNotFoundThrow_ReosureNotFoundExp() throws CustomExceptions, ResourceNotFoundException {

		when(repo.findByHospitalEmailAndIsArchived(app.getHospitalEmail(),false)).thenReturn(Collections.emptyList());
		ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
			service.findByHospEmail(app.getHospitalEmail(),false);
		});


//		 Assert
		verify(repo, times(1)).findByHospitalEmailAndIsArchived(app.getHospitalEmail(),false);
		assertEquals(exception.getMessage(), "Appointment not found with Hospital-Email : "+app.getHospitalEmail());

	}
	@Test
	public void findByHospEmailIfFoundReturn_AppointmentList() throws CustomExceptions, ResourceNotFoundException {

		when(repo.findByHospitalEmailAndIsArchived(app.getHospitalEmail(),false)).thenReturn(List.of(app));
		
		List<AppointMent> result = service.findByHospEmail(app.getHospitalEmail(),false);

		// Assert
		verify(repo, times(1)).findByHospitalEmailAndIsArchived(app.getHospitalEmail(),false);

		assertEquals(1, result.size());
	}
}
