package com.service.organisation.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.service.organisation.entity.Organisation;
import com.service.organisation.exceptions.CustomExceptions;
import com.service.organisation.exceptions.ResourceNotFoundException;
import com.service.organisation.repositories.OrgRepository;
import com.service.organisation.services.OrgService;
import com.service.organisation.util.Helper;

@SpringBootTest
public class OrganisationServiceTest {

	@Autowired
	private OrgService orgService;

	@MockBean
	private OrgRepository orgRepo;

	Organisation org = null;

	private static final String AGENCY_Email = "agency@agency.com";

	@BeforeEach
	public void getOrg() {
		org = new Organisation();
		org.setOrganisationEmail("org@org.com");
		org.setPassword("password");
	}

	@AfterEach
	public void disposeOrg() {
		org = null;
	}

	@Test
	public void createOrg_withNewOrganisation_shouldReturnOrganisation() throws CustomExceptions {

		when(orgRepo.findByOrganisationEmail(org.getOrganisationEmail())).thenReturn(Optional.ofNullable(null));
		when(orgRepo.save(org)).thenReturn(org);

		// Act
		Organisation result = orgService.createOrg(org);

		// Assert
		verify(orgRepo, times(1)).findByOrganisationEmail(org.getOrganisationEmail());
		verify(orgRepo, times(1)).save(org);
		assertNotNull(result);
		assertEquals(org, result);
	}

	@Test
	public void createOrg_withExistingOrganisation_shouldThrowCustomExceptions() {

		when(orgRepo.findByOrganisationEmail(org.getOrganisationEmail())).thenReturn(Optional.of(org));

		// Act and Assert
		CustomExceptions exception = assertThrows(CustomExceptions.class, () -> {
			orgService.createOrg(org);
		});

		// Assert
		verify(orgRepo, times(1)).findByOrganisationEmail(org.getOrganisationEmail());
		verify(orgRepo, never()).save(org);
		assertEquals("Organisation Already Exist with the email :  org@org.com", exception.getMessage());
	}

	@Test
	public void getByEmail_when_NoEmailPresent_shouldThrowResoureNotFoundException() throws ResourceNotFoundException {

		when(orgRepo.findByOrganisationEmail("org@org.com")).thenReturn(Optional.ofNullable(null));

		ResourceNotFoundException exp = assertThrows(ResourceNotFoundException.class,
				() -> orgService.getByEmail("org@org.com"));
		verify(orgRepo, times(1)).findByOrganisationEmail(org.getOrganisationEmail());
		assertEquals("Orgaisation not found with email : org@org.com", exp.getMessage());
	}

	@Test
	public void getByEmail_when_EmailPresent_should_ReturnOrganisation() throws ResourceNotFoundException {

		when(orgRepo.findByOrganisationEmail(org.getOrganisationEmail())).thenReturn(Optional.of(org));

		Organisation result = orgService.getByEmail("org@org.com");
		verify(orgRepo, times(1)).findByOrganisationEmail("org@org.com");
		assertNotNull(result);
		assertEquals(org, result);
	}

	@Test
	public void deleteByEmail_when_NoEmailPresent_shouldThrowResoureNotFoundException()
			throws ResourceNotFoundException {

		when(orgRepo.findByOrganisationEmail("org@org.com")).thenReturn(Optional.ofNullable(null));

		ResourceNotFoundException exp = assertThrows(ResourceNotFoundException.class,
				() -> orgService.deleteByEmail("org@org.com"));
		verify(orgRepo, times(1)).findByOrganisationEmail(org.getOrganisationEmail());
		assertEquals("Orgaisation not found with email : org@org.com", exp.getMessage());
	}

	@Test
	public void getByEmail_when_EmailPresent_should_deleteOrganisation() throws ResourceNotFoundException {

		when(orgRepo.findByOrganisationEmail(org.getOrganisationEmail())).thenReturn(Optional.of(org));

		Organisation result = orgService.deleteByEmail("org@org.com");
		verify(orgRepo, times(1)).findByOrganisationEmail("org@org.com");
		assertNotNull(result);
		assertEquals(org, result);
	}

	// find All by Agency Email
	@Test
	public void findByAgencyEmail_when_AgencyPresent_should_returnOrganisations() throws CustomExceptions {

		when(orgRepo.findByInsuranceAgencyEmail(AGENCY_Email))
				.thenReturn(Stream
						.of(new Organisation(AGENCY_Email, "org1", "org1@org.com", "pass1"),
								new Organisation(AGENCY_Email, "org2", "org2@org.com", "pass2"),
								new Organisation(AGENCY_Email, "org3", "org3@org.com", "pass3"),
								new Organisation(AGENCY_Email, "org4", "org4@org.com", "pass4"))
						.collect(Collectors.toList()));
		assertEquals(4, orgService.getByAgencyEmail(AGENCY_Email).size());
		verify(orgRepo, times(1)).findByInsuranceAgencyEmail(AGENCY_Email);

	}

	// throw Excep
	@Test
	public void findByAgencyEmail_when_Agency_Not_Present_should_thorwoCustomExp() throws CustomExceptions {

		when(orgRepo.findByInsuranceAgencyEmail(AGENCY_Email)).thenReturn(Collections.emptyList());

		CustomExceptions ex = assertThrows(CustomExceptions.class,
				() -> orgService.getByAgencyEmail(AGENCY_Email));

		verify(orgRepo, times(1)).findByInsuranceAgencyEmail(AGENCY_Email);
		assertEquals("Organisations Not Found with agency the email :  " + AGENCY_Email, ex.getMessage());

	}

	@Test
	public void deleteAllByAgency_when_EmailPresent_should_deleteOrganisation()
			throws ResourceNotFoundException, CustomExceptions {

		when(orgRepo.findByInsuranceAgencyEmail(AGENCY_Email))
				.thenReturn(Stream
						.of(new Organisation(AGENCY_Email, "org1", "org1@org.com", "pass1"),
								new Organisation(AGENCY_Email, "org2", "org2@org.com", "pass2"),
								new Organisation(AGENCY_Email, "org3", "org3@org.com", "pass3"),
								new Organisation(AGENCY_Email, "org4", "org4@org.com", "pass4"))
						.collect(Collectors.toList()));

		List<Organisation> orgs = orgService.deleteByAgencyEmail(AGENCY_Email);
		verify(orgRepo, times(1)).findByInsuranceAgencyEmail(AGENCY_Email);
		assertNotNull(orgs);
		assertEquals(4, orgs.size());
	}

	@Test
	public void deleteAllByAgency_when_Email_NOT_Present_should_throwCustomExp() throws CustomExceptions {

		when(orgRepo.findByInsuranceAgencyEmail(AGENCY_Email)).thenReturn(Collections.emptyList());

		CustomExceptions ex = assertThrows(
				CustomExceptions.class,
				() -> orgService.deleteByAgencyEmail(AGENCY_Email));

		verify(orgRepo, times(1)).findByInsuranceAgencyEmail(AGENCY_Email);
		
		assertEquals("Organisations Not Found with agency the email :  "
		              + AGENCY_Email, ex.getMessage());

	}

}
