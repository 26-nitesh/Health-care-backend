package com.agencyservice.agency.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.agencyservice.agency.entity.Agency;
import com.agencyservice.agency.exceptions.CustomExceptions;
import com.agencyservice.agency.exceptions.ResourceNotFoundException;
import com.agencyservice.agency.repo.AgencyRepo;
import com.agencyservice.agency.services.AgencyService;


@SpringBootTest
public class AgencyServiceTest {

	
	Agency agency = null;
	
	@Autowired AgencyService service;
	
	@MockBean private AgencyRepo repo;
	
	@BeforeEach
	public void setuP() {
		agency = new Agency("a@a","pass");
	}
	
	@AfterEach
	public void drop() {
		agency = null;
	}
	
	
	@Test
	public void createHosp_withNewHospital_shouldReturnHospital() throws CustomExceptions, ResourceNotFoundException {

		when(repo.findByAgencyEmail(agency.getAgencyEmail())).thenReturn(Optional.ofNullable(null));
		when(repo.save(agency)).thenReturn(agency);

		// Act
		
	  Agency result = service.createNewAgency(agency);
		
		// Assert
		verify(repo, times(1)).findByAgencyEmail(agency.getAgencyEmail());
		verify(repo, times(1)).save(agency);
		assertNotNull(result);
		assertEquals(agency, result);
	}
	
	@Test
	public void createHosp_withExistingHosp_shouldThrowCustomExceptions() {

		when(repo.findByAgencyEmail(agency.getAgencyEmail())).thenReturn(Optional.of(agency));

		// Act and Assert
		CustomExceptions exception = assertThrows(CustomExceptions.class, () -> {
			service.createNewAgency(agency);
		});

		// Assert
		verify(repo, times(1)).findByAgencyEmail(agency.getAgencyEmail());
		verify(repo, never()).save(agency);
		assertEquals("Agency Already Exist with the email :  a@a", exception.getMessage());
	}
}
