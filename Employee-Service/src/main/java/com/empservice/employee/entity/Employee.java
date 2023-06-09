package com.empservice.employee.entity;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "employee_TBL")
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int employeeId;

	@Column(nullable = false)
	private String orgEmail;

	@Column(unique = true, nullable = false)
	private String empEmail;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private String empName;

	private String empDesignation;
	private LocalDate dob;
	private LocalDate dateOfJoining;
	private boolean isHazardousExposure;//isHazardousExposure

	private LocalDate lastCheckupDate;
	private String addLine1;
	private String city;
	private String zip;
    private int checkupCount;
	public Employee() {
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
	

	public int getCheckupCount() {
		return checkupCount;
	}

	public void setCheckupCount(int checkupCount) {
		this.checkupCount = checkupCount;
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

	
	public boolean getIsHazardousExposure() {
		return isHazardousExposure;
	}

	public void setIsHazardousExposure(boolean isHazardousExposure) {
		this.isHazardousExposure = isHazardousExposure;
	}

	public Employee(String empEmail, String orgEmail, String empName, String password) {
		super();
		this.empEmail = empEmail;
		this.orgEmail = orgEmail;
		this.empName = empName;
		this.password = password;
	}

	
}
