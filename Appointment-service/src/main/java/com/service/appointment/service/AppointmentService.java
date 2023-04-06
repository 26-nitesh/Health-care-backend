package com.service.appointment.service;

import java.util.List;

import com.service.appointment.entity.AppointMent;

public interface AppointmentService {

	AppointMent createnewAppointMent(AppointMent appointment);

	List<AppointMent> findByEmpEmail(String email);

	List<AppointMent> findByHospEmail(String email);

	List<AppointMent> deleteByHospital(String email);

	List<AppointMent> deleteByEmpEmail(String email);

	List<AppointMent> findByHospAndEmp(String empEmail, String hospEmail);

	List<AppointMent> deleteByHospAndEmp(String empEmail, String hospEmail);

}
