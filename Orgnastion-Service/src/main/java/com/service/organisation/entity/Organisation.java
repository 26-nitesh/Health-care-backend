package com.service.organisation.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "organisation_tbl")
public class Organisation {

//	organisation_name varchar
//	organisation_email varchar
//	organisation_address varchar
//	password varchar
//	insurence_agency_id int [ref: - insurence_agency.agency_id]
//	health_checkup_policy varchar [ref: > policy.policy_id]

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int orgId;
	private String insuranceAgencyEmail;
	private String organisationName;
	@Column(nullable = false,unique = true)
	private String organisationEmail;
	@Column(nullable = false)
	private String password;
	private String addLine1;
	private String city;
	private String zip;
	
	public Organisation() {
		// TODO Auto-generated constructor stub
	}

	public int getOrgId() {
		return orgId;
	}

	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}

	

	public String getInsuranceAgencyEmail() {
		return insuranceAgencyEmail;
	}

	public void setInsuranceAgencyEmail(String insuranceAgencyEmail) {
		this.insuranceAgencyEmail = insuranceAgencyEmail;
	}

//	public List<Integer> getPolicyIds() {
//		return policyIds;
//	}
//
//	public void setPolicyIds(List<Integer> policyIds) {
//		this.policyIds = policyIds;
//	}

	public String getOrganisationName() {
		return organisationName;
	}

	public void setOrganisationName(String organisationName) {
		this.organisationName = organisationName;
	}

	public String getOrganisationEmail() {
		return organisationEmail;
	}

	public void setOrganisationEmail(String organisationEmail) {
		this.organisationEmail = organisationEmail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAddLine1() {
		return addLine1;
	}

	public void setAddLine1(String addLine1) {
		this.addLine1 = addLine1;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public Organisation(String insuranceAgencyEmail, String organisationName, String organisationEmail,
			String password) {
		super();
		this.insuranceAgencyEmail = insuranceAgencyEmail;
		this.organisationName = organisationName;
		this.organisationEmail = organisationEmail;
		this.password = password;
	}

	
	
	
	
}
