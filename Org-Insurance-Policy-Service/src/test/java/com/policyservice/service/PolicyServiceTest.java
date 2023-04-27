package com.policyservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
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

import com.policyservice.entity.Policy;
import com.policyservice.exceptions.CustomExceptions;
import com.policyservice.exceptions.ResourceNotFoundException;
import com.policyservice.repo.PolicyRepo;

@SpringBootTest
public class PolicyServiceTest {


	@Autowired
	private PolicyService service;

	@MockBean
	private PolicyRepo repo;

	Policy policy = null;


	@BeforeEach
	public void getOrg() {
		  MockitoAnnotations.openMocks(this);
		policy = new Policy();
		policy.setOrgEmail("o@o");
		policy.setFrequency("2");
		policy.setPolicyName("base");
	}

	@AfterEach
	public void dispose() {
	policy = null;
	}
	@Test
	public void createPolicywithNew_Policy() throws CustomExceptions, ResourceNotFoundException {

		when(repo.findByOrgEmailAndPolicyName(policy.getOrgEmail(), policy.getPolicyName())).thenReturn(Optional.empty());
		when(repo.save(policy)).thenReturn(policy);

		// Act
		Policy result = service.createPolicy(policy);

		// Assert
		verify(repo, times(1)).findByOrgEmailAndPolicyName(policy.getOrgEmail(), policy.getPolicyName());
		verify(repo, times(1)).save(policy);
		assertNotNull(result);
		assertEquals(policy, result);

	}
	
	@Test
	public void createPolicywithNew_ExistingPolicyThrowCustomExp() throws CustomExceptions, ResourceNotFoundException {

		when(repo.findByOrgEmailAndPolicyName(policy.getOrgEmail(), policy.getPolicyName())).thenReturn(Optional.of(policy));
		
		ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, ()->service.createPolicy(policy));


		// Assert
		verify(repo, times(1)).findByOrgEmailAndPolicyName(policy.getOrgEmail(), policy.getPolicyName());
		assertEquals(exception.getMessage(), "Policy Already Exist with given name : "+policy.getPolicyName());

	}
	
	@Test
	public void getAllPolicy_ifNotfound_Throw_ResourceNotFoundException() throws CustomExceptions, ResourceNotFoundException {

		when(repo.findByOrgEmail(policy.getOrgEmail())).thenReturn(Collections.emptyList());
		
		ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, ()->service.getAllPolicyByOrgEmail(policy.getOrgEmail()));


		// Assert
		verify(repo, times(1)).findByOrgEmail(policy.getOrgEmail());
		assertEquals(exception.getMessage(), "policies not found with email : "+policy.getOrgEmail());

	}
	@Test
	public void getAllPolicy_iffound_ReturnPolicyList() throws CustomExceptions, ResourceNotFoundException {

		when(repo.findByOrgEmail(policy.getOrgEmail())).thenReturn(List.of(new Policy("o@o","name1"),policy,new Policy("o@o","name3")));
		
		List<Policy> result = service.getAllPolicyByOrgEmail(policy.getOrgEmail());


		// Assert
		verify(repo, times(1)).findByOrgEmail(policy.getOrgEmail());
		assertEquals(3,result.size());

	}
	
}
