package com.service.appointment.entity;

import java.time.LocalDate;
import java.util.Date;

import javax.annotation.Generated;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection  = "data_appointment")
public class AppointMent {
//	Table public.Appointment as Appointment {
//		appointment_id int [pk]
//		emp_id int [ref: - org.org_id]
//		appointment_date datetime
//		appointment_status varchar
//		isVerfied boolean
//		remarks varchar
//		hospital_id int [ref: > hospital.hospital_id]
//		}

	
	@Id
	private int appintmentId;
	private String employeeEmail;
	private String hospitalEmail;
	private LocalDate appointmentDate;
	private String status;
	private boolean isVerified;
	private String remarks;
	private double amount;
	private boolean isArchived;
	private LocalDate bookingDate;
	
	public AppointMent() {
		// TODO Auto-generated constructor stub
	}

	public boolean getIsArchived() {
		return isArchived;
	}

	public void setIsArchived(boolean isArchived) {
		this.isArchived = isArchived;
	}

	public int getAppintmentId() {
		return appintmentId;
	}

	public void setAppintmentId(int appintmentId) {
		this.appintmentId = appintmentId;
	}

	public String getEmployeeEmail() {
		return employeeEmail;
	}

	public LocalDate getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(LocalDate bookingDate) {
		this.bookingDate = bookingDate;
	}

	public void setEmployeeEmail(String employeeEmail) {
		this.employeeEmail = employeeEmail;
	}

	public String getHospitalEmail() {
		return hospitalEmail;
	}

	public void setHospitalEmail(String hospitalEmail) {
		this.hospitalEmail = hospitalEmail;
	}

	


	public LocalDate getAppointmentDate() {
		return appointmentDate;
	}

	public void setAppointmentDate(LocalDate appointmentDate) {
		this.appointmentDate = appointmentDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isVerified() {
		return isVerified;
	}

	public void setVerified(boolean isVerified) {
		this.isVerified = isVerified;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	
	
}
