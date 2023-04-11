package com.policyservice.entity;

import java.util.Map;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
public class Policy {

	/*
	 * Examples of policies
	 * All employees are eligible for a health checkup once a year.
	 * Employees who have been with the organization for at least 6 months are eligible for a health checkup.
	 * Employees who work in positions that involve exposure to hazardous materials or chemicals are eligible for a health checkup every 6 months.
	 * Employees who are over the age of 40 are eligible for a health checkup every 2 years.
	 * */
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int policyId;
	@Column(nullable = false)
	private String orgEmail;
	
	private String policyName;
		
	private int age;
	
	private int minMonthOfService;
			
	private int frequency;

	public int getPolicyId() {
		return policyId;
	}

	public void setPolicyId(int policyId) {
		this.policyId = policyId;
	}

	public String getOrgEmail() {
		return orgEmail;
	}

	public void setOrgEmail(String orgEmail) {
		this.orgEmail = orgEmail;
	}

	public String getPolicyName() {
		return policyName;
	}

	public void setPolicyName(String policyName) {
		this.policyName = policyName;
	}

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}



	public int getMinMonthOfService() {
		return minMonthOfService;
	}

	public void setMinMonthOfService(int minMonthOfService) {
		this.minMonthOfService = minMonthOfService;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	
	
}