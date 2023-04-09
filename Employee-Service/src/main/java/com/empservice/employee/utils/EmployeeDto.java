package com.empservice.employee.utils;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;

public class EmployeeDto implements Serializable{


	private int employeeId;
	private String orgEmail;
		private String empEmail;
	
	private String password;
	
	private String empName;
	
	private String empDesignation;
	private LocalDate dob;
	private LocalDate dateOfJoining;
	private boolean isHazardousExposure;
	private LocalDate lastCheckupDate;
//	private boolean 
	private String addLine1;
	private String city;
	private String zip;
	public EmployeeDto() {
		// TODO Auto-generated constructor stub
	}
	public int getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	
	public String getOrgEmail() {
		return orgEmail;
	}
	public void setOrgEmail(String orgEmail) {
		this.orgEmail = orgEmail;
	}
	public String getEmpEmail() {
		return empEmail;
	}
	public void setEmpEmail(String empEmail) {
		this.empEmail = empEmail;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getEmpDesignation() {
		return empDesignation;
	}
	public void setEmpDesignation(String empDesignation) {
		this.empDesignation = empDesignation;
	}

	public LocalDate getDob() {
		return dob;
	}
	public void setDob(LocalDate dob) {
		this.dob = dob;
	}
	public LocalDate getDateOfJoining() {
		return dateOfJoining;
	}
	public void setDateOfJoining(LocalDate dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}
	
	
	public boolean isHazardousExposure() {
		return isHazardousExposure;
	}
	public void setHazardousExposure(boolean isHazardousExposure) {
		this.isHazardousExposure = isHazardousExposure;
	}
	public LocalDate getLastCheckupDate() {
		return lastCheckupDate;
	}
	public void setLastCheckupDate(LocalDate lastCheckupDate) {
		this.lastCheckupDate = lastCheckupDate;
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
	
}
