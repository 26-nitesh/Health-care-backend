package com.service.report.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.service.report.entity.Report;
import com.service.report.exceptions.CustomExceptions;
import com.service.report.exceptions.ResourceNotFoundException;
import com.service.report.repo.ReportRepo;

@Service
public class ReportServiceImpl implements ReportService {

	@Autowired private ReportRepo reportRepo;
	
	@Override
	public Report createReport(Report report) throws CustomExceptions {

			if(checkIfReportAlreadyExist(report.getAppointmentId())==null) {
			  report.setReportId(getUniqueId());
			return reportRepo.save(report);
			}else
			throw new CustomExceptions("Report already Exist for given appointment ");
	}

	private int getUniqueId() {
		int id = (int) (Math.random()*1000);
		Optional<Report> findById = reportRepo.findById(id);
		if(findById.isPresent()) {
			return getUniqueId();
		}else {
			return id;
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
        Optional<Report> report= reportRepo.findByAppointmentId(id);
		if(report.isPresent()) {
			return report.get();
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
		Report dataToUpdate = checkIfReportAlreadyExist(report.getAppointmentId());
		if(dataToUpdate!=null) {
			System.out.println(dataToUpdate.getAppointmentId());
//			dataToUpdate.setAppointmentDate(report.getAppointmentDate());
			dataToUpdate.setRemarks(report.getRemarks());
			dataToUpdate.setReportDetails(report.getReportDetails());
			return reportRepo.save(dataToUpdate);
		}
		throw new ResourceNotFoundException("Report", "Appointment Id", String.valueOf(report.getReportId()));
	}

}
