package com.service.report.service;

import com.service.report.entity.Report;
import com.service.report.exceptions.CustomExceptions;
import com.service.report.exceptions.ResourceNotFoundException;

public interface ReportService {

	Report createReport(Report report) throws CustomExceptions;

	Report findByAppId(int id) throws ResourceNotFoundException;

	Report deleteByAppId(int id) throws ResourceNotFoundException;

	Report updateReport(Report report) throws ResourceNotFoundException;

}
