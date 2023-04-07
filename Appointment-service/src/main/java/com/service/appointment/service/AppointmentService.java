package com.service.appointment.service;

import java.util.List;

import com.service.appointment.entity.AppointMent;
import com.service.appointment.exceptions.CustomExceptions;
import com.service.appointment.exceptions.ResourceNotFoundException;

public interface AppointmentService {

	AppointMent createnewAppointMent(AppointMent appointment) throws CustomExceptions;

	List<AppointMent> findByEmpEmail(String email) throws ResourceNotFoundException;

	List<AppointMent> findByHospEmail(String email) throws ResourceNotFoundException;

	List<AppointMent> deleteByHospital(String email) throws CustomExceptions;

	List<AppointMent> deleteByEmpEmail(String email) throws CustomExceptions;

	List<AppointMent> findByHospAndEmp(String empEmail, String hospEmail) throws ResourceNotFoundException;

	List<AppointMent> deleteByHospAndEmp(String empEmail, String hospEmail) throws CustomExceptions;

}
