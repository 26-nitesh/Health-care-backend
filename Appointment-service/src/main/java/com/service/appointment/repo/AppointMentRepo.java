package com.service.appointment.repo;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.service.appointment.entity.AppointMent;

@Repository
public interface AppointMentRepo extends MongoRepository<AppointMent, Integer> {
	Optional<AppointMent> findByAppintmentId(int id);
    List<AppointMent> findByEmployeeEmailOrderByBookingDateDesc(String employeeEmail);
	List<AppointMent> findByEmployeeEmail(String email);
	List<AppointMent> findByHospitalEmail(String email);
	List<AppointMent> findByHospitalEmailAndIsArchived(String email,boolean flag);
	List<AppointMent> findByEmployeeEmailAndHospitalEmail(String empEmail,String hospEmail);
	Optional<AppointMent> findByEmployeeEmailAndAppointmentDate(String email,LocalDate appDate);
	Optional<AppointMent> findByEmployeeEmailAndIsArchived(String email, boolean flag);
	 List<AppointMent> findByHospitalEmailOrderByClaimDateDesc(String hospEmail);
	
	 
}
