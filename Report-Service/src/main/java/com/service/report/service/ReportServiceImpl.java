package com.service.report.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.service.report.entity.Report;
import com.service.report.exceptions.CustomExceptions;
import com.service.report.exceptions.ResourceNotFoundException;
import com.service.report.repo.ReportRepo;

@Service
public class ReportServiceImpl implements ReportService {

	@Autowired private ReportRepo reportRepo;
	
	@Override
	public Report createReport(Report report) throws CustomExceptions {
		try {
			if(checkIfReportAlreadyExist(report.getReportId())==null)
			return reportRepo.save(report);
			throw new CustomExceptions("Report already Exist for give appointment "+report.getReportId());
		} catch (Exception e) {
		   throw new CustomExceptions("failed to create : "+e.getMessage());
		}
	}

	@Override
	public Report findByAppId(int id) throws ResourceNotFoundException {
		Report report = checkIfReportAlreadyExist(id);
		if(report!=null) {
			return report;
		}
		throw new ResourceNotFoundException("Report", "Appointment Id", String.valueOf(id));
	}
	private Report checkIfReportAlreadyExist(int id) {
        Optional<Report> orgByEmail = reportRepo.findByAppointmentId(id);
		if(orgByEmail.isPresent()) {
			return orgByEmail.get();
		}
		return null;
	}

	@Override
	public Report deleteByAppId(int id) throws ResourceNotFoundException {
		Report report = checkIfReportAlreadyExist(id);
		if(report!=null) {
			reportRepo.delete(report);
			return report;
		}
		throw new ResourceNotFoundException("Report", "Appointment Id", String.valueOf(id));
	}

	@Override
	public Report updateReport(Report report) throws ResourceNotFoundException {
		Report dataToUpdate = checkIfReportAlreadyExist(report.getReportId());
		if(dataToUpdate!=null) {
			
			dataToUpdate.setAppointmentDate(report.getAppointmentDate());
			dataToUpdate.setStatus(report.getStatus());
			dataToUpdate.setReportDetails(report.getReportDetails());
			return reportRepo.save(dataToUpdate);
		}
		throw new ResourceNotFoundException("Report", "Appointment Id", String.valueOf(report.getReportId()));
	}

}
