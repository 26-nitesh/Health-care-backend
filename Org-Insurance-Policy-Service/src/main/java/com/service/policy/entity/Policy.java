package com.service.policy.entity;

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

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int policyId;
	@Column(nullable = false)
	private String orgEmail;
	
	@ElementCollection
	Map<String, String> policyKeyValue;

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

	public Map<String, String> getPolicyKeyValue() {
		return policyKeyValue;
	}

	public void setPolicyKeyValue(Map<String, String> policyKeyValue) {
		this.policyKeyValue = policyKeyValue;
	}
	
	
}
