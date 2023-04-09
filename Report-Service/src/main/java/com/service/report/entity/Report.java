package com.service.report.entity;

import java.time.LocalDate;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "DatRreport")
public class Report {

//	 Table public.Report as Report {
//		 report_id int [pk]
//		 appointment_id int [ref: - Appointment.appointment_id]
//		 report_details blob
//		 appointment_date datetime
//		 appointment_status varchar
//		 }
	
	@Id
	private int reportId;
	private int appointmentId;
 
	private String reportDetails;
	private Date appointmentDate;
	private String status;
	
	
	public int getReportId() {
		return reportId;
	}
	public void setReportId(int reportId) {
		this.reportId = reportId;
	}
	public int getAppointmentId() {
		return appointmentId;
	}
	public void setAppointmentId(int appointmentId) {
		this.appointmentId = appointmentId;
	}
	public String getReportDetails() {
		return reportDetails;
	}
	public void setReportDetails(String reportDetails) {
		this.reportDetails = reportDetails;
	}
	public Date getAppointmentDate() {
		return appointmentDate;
	}
	public void setAppointmentDate(Date appointmentDate) {
		this.appointmentDate = appointmentDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
