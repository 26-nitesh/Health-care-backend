package com.service.report.entity;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Document(collection = "DataReport")
public class Report {

	
	@Id
	private int reportId;
	private int appointmentId;
 
	private String reportDetails;
	private LocalDate appointmentDate;
	private String remarks;
	

	private byte[] reportFileData;

    
    

	public byte[] getReportFileData() {
		return reportFileData;
	}
	public void setReportFileData(byte[] reportFileData) {
		this.reportFileData = reportFileData;
	}
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
	public LocalDate getAppointmentDate() {
		return appointmentDate;
	}
	public void setAppointmentDate(LocalDate appointmentDate) {
		this.appointmentDate = appointmentDate;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Report() {
		// TODO Auto-generated constructor stub
	}
	public Report(int appointmentId, String reportDetails, LocalDate appointmentDate, String remarks,
			byte[] reportFileData) {
		super();
		this.appointmentId = appointmentId;
		this.reportDetails = reportDetails;
		this.appointmentDate = appointmentDate;
		this.remarks = remarks;
		this.reportFileData = reportFileData;
	}
	
	
}
