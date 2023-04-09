package com.service.report.repo;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.service.report.entity.Report;

public interface ReportRepo extends MongoRepository<Report, Integer> {

	Optional<Report> findByAppointmentId(int id);
}
