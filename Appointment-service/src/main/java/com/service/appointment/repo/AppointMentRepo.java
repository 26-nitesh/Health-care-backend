package com.service.appointment.repo;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.service.appointment.entity.AppointMent;

@Repository
public interface AppointMentRepo extends MongoRepository<AppointMent, Integer> {

	List<AppointMent> findByEmployeeEmail(String email);
	List<AppointMent> findByHospitalEmail(String email);
	List<AppointMent> findByEmployeeEmailAndHospitalEmail(String empEmail,String hospEmail);
	Optional<AppointMent> findByEmployeeEmailAndAppointmentDate(String email,LocalDate appDate);
//	Optional<AppointMent> 
}
