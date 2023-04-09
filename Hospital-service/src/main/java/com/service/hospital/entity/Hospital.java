package com.service.hospital.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Hospital {

//	Table public.hospital as hospital {
//		hospital_id int [pk]
//		hospital_name varchar
//		hospital_email varchar
//		password varchar
//		hospital_city varchar
//		hospital_state varchar
//		hospital_zip varchar
//		agency_id int [ref: > insurence_agency.agency_id] 
//		}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int hospitalId;
	private String hospitalName;
	@Column(unique = true,nullable = false)
	private String hospitalEmail;
	private String password;
	private String agencyEmail;
	private String city;
	private String state;
	private String zip;
//	
//	@ElementCollection
//	private List<String> agencies;
	
	public int getHospitalId() {
		return hospitalId;
	}
	public void setHospitalId(int hospitalId) {
		this.hospitalId = hospitalId;
	}
	public String getHospitalName() {
		return hospitalName;
	}
	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}
	public String getHospitalEmail() {
		return hospitalEmail;
	}
	public void setHospitalEmail(String hospitalEmail) {
		this.hospitalEmail = hospitalEmail;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getAgencyEmail() {
		return agencyEmail;
	}
	public void setAgencyEmail(String agencyEmail) {
		this.agencyEmail = agencyEmail;
	}
	
	
	
}
