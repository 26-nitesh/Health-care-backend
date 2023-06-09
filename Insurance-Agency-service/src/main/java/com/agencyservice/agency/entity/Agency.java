package com.agencyservice.agency.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Agency {

	/*
	 
	 Table public.Insurance_Agency as insurence_agency {
     agency_id int [pk]
     agency_name varchar
	 agency_email varchar
	 password varchar
     agency_addLine1 varchar
     agency_city varchar
     agency_state varchar
     Agency_zip varchar
     hospital_id int [ref: > hospital.hospital_id]

	 */
	
	
	@Id
	@GeneratedValue(strategy =  GenerationType.AUTO)
	private int agencyId;
	
	private String agencyName;
	
	@Column(nullable = false,unique = true)
	private String agencyEmail;
	
	private String password;
	
	private String addLine1;
	private String city;
	private String zip;
	
	public int getAgencyId() {
		return agencyId;
	}
	public void setAgencyId(int agencyId) {
		this.agencyId = agencyId;
	}
	public String getAgencyName() {
		return agencyName;
	}
	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}
	public String getAgencyEmail() {
		return agencyEmail;
	}
	public void setAgencyEmail(String agencyEmail) {
		this.agencyEmail = agencyEmail;
	}
//	public String getHospitalEmail() {
//		return hospitalEmail;
//	}
//	public void setHospitalEmail(String hospitalEmail) {
//		this.hospitalEmail = hospitalEmail;
//	}
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
	public Agency() {
		// TODO Auto-generated constructor stub
	}
	public Agency(String agencyEmail, String password) {
		super();
		this.agencyEmail = agencyEmail;
		this.password = password;
	}
	public Agency(String agencyEmail, String agencyName, String password) {
	  this.agencyEmail=agencyEmail;
	  this.agencyName = agencyName;
	  this.password=password;
	}
	
	
	
}
