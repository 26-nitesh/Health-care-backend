package com.service.hospital.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.service.hospital.entity.Hospital;
import com.service.hospital.exceptions.CustomExceptions;
import com.service.hospital.exceptions.ResourceNotFoundException;
import com.service.hospital.repo.HospitalRepo;

import io.jsonwebtoken.lang.Collections;



@SpringBootTest
public class HospitalServiceTest {

	Hospital hosp;
	@Autowired HospitalService hospitalService;
	
	@MockBean HospitalRepo hospitalRepo;
	
	@BeforeEach
	public void getOrg() {
		hosp = new Hospital();
		hosp.setHospitalEmail("h@h.com");
		hosp.setPassword("password");
	}

	@AfterEach
	public void disposeOrg() {
		hosp = null;
	}
	
	@Test
	public void createHosp_withNewHospital_shouldReturnHospital() throws CustomExceptions, ResourceNotFoundException {

		when(hospitalRepo.findByHospitalEmail(hosp.getHospitalEmail())).thenReturn(Optional.ofNullable(null));
		when(hospitalRepo.save(hosp)).thenReturn(hosp);

		// Act
		
		Hospital createHospital = hospitalService.createHospital(hosp);
		
		// Assert
		verify(hospitalRepo, times(1)).findByHospitalEmail(hosp.getHospitalEmail());
		verify(hospitalRepo, times(1)).save(hosp);
		assertNotNull(createHospital);
		assertEquals(hosp, createHospital);
	}
	
	@Test
	public void createHosp_withExistingHosp_shouldThrowCustomExceptions() {

		when(hospitalRepo.findByHospitalEmail(hosp.getHospitalEmail())).thenReturn(Optional.of(hosp));

		// Act and Assert
		CustomExceptions exception = assertThrows(CustomExceptions.class, () -> {
			hospitalService.createHospital(hosp);
		});

		// Assert
		verify(hospitalRepo, times(1)).findByHospitalEmail(hosp.getHospitalEmail());
		verify(hospitalRepo, never()).save(hosp);
		assertEquals("Hospital Already Exist with the email :  h@h.com", exception.getMessage());
	}

	@Test
	public void FindAllHostptalTest() throws CustomExceptions {
		when(hospitalRepo.findAll()).thenReturn(Stream.of(new Hospital("e@e","pass"),
				                                          new Hospital("e@e","pass"),
				                                          new Hospital("e@e","pass")).collect(Collectors.toList()));
	   
		List<Hospital> allHospitals = hospitalService.getAllHospitals();
	     verify(hospitalRepo,times(1)).findAll();
	     assertNotNull(allHospitals);
	     assertEquals(3, allHospitals.size());
	
	}
	
	@Test
	public void getByAgencyIfNoAgencyFoundThenThrowResourceNotFoundException() throws ResourceNotFoundException{
		
		when(hospitalRepo.findByAgencyEmail("a@a.com")).thenReturn(java.util.Collections.emptyList());
		
//		List<Hospital> result = hospitalService.findByAgency("a@a.com");
		ResourceNotFoundException exp = assertThrows(ResourceNotFoundException.class, ()-> hospitalService.findByAgency("a@a.com"));
		verify(hospitalRepo,times(1)).findByAgencyEmail("a@a.com");
		assertEquals("Hospitals not found with Agency Email : a@a.com", exp.getMessage());
	}
	
	@Test
	public void getByAgencyIfFoundThenReturnListOfHospital() throws ResourceNotFoundException{
		List<Hospital> collect = Stream.of(new Hospital("e@e","pass","a@a.com"),
                new Hospital("e@e","pass","a@a.com"),
                new Hospital("e@e","pass","a@a.com")).collect(Collectors.toList());
		
		when(hospitalRepo.findByAgencyEmail("a@a.com")).thenReturn(collect);
		
		List<Hospital> result = hospitalService.findByAgency("a@a.com");
		
		assertEquals(3, result.size());
		verify(hospitalRepo,times(1)).findByAgencyEmail("a@a.com");
		assertNotNull(result);
	}
	
	
}
