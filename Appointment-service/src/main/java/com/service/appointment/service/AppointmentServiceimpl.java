package com.service.appointment.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.service.appointment.entity.AppointMent;
import com.service.appointment.exceptions.CustomExceptions;
import com.service.appointment.exceptions.ResourceNotFoundException;
import com.service.appointment.repo.AppointMentRepo;

@Service
public class AppointmentServiceimpl implements AppointmentService{

	@Autowired AppointMentRepo appoinmentRepo;
	@Override
	public AppointMent createnewAppointMent(AppointMent appointment) throws CustomExceptions {
	
		try {
		    return	appoinmentRepo.save(appointment);
		}catch (Exception e) {
			throw new CustomExceptions("exception occured while creating appointment  :",e.getMessage());
		}
	}
	@Override
	public List<AppointMent> findByEmpEmail(String email) throws ResourceNotFoundException {
		List<AppointMent> apps = appoinmentRepo.findByEmployeeEmail(email);
		if(!apps.isEmpty()) {
		return apps;	
		}
		throw new ResourceNotFoundException("Appointment", "Employee-Email", email);
		
	}
	@Override
	public List<AppointMent> findByHospEmail(String email) throws ResourceNotFoundException {
		List<AppointMent> apps = appoinmentRepo.findByHospitalEmail(email);
		if(!apps.isEmpty()) {
		return apps;	
		}
		throw new ResourceNotFoundException("Appointment", "Hospital-Email", email);
	}
	
	void deleteAppintments(List<AppointMent> apps) throws CustomExceptions {
		try {
			appoinmentRepo.deleteAll(apps);
		}catch (Exception e) {
			throw new CustomExceptions("exception occured while deleting appointment  :",e.getMessage());
		}
	}
	
	@Override
	public List<AppointMent> deleteByHospital(String email) throws CustomExceptions {
		List<AppointMent> apps = appoinmentRepo.findByHospitalEmail(email);
		deleteAppintments(apps);
		return apps;
	}
	@Override
	public List<AppointMent> deleteByEmpEmail(String email) throws CustomExceptions {
		List<AppointMent> apps = appoinmentRepo.findByEmployeeEmail(email);
		deleteAppintments(apps);
		return apps;
	}
	@Override
	public List<AppointMent> findByHospAndEmp(String empEmail, String hospEmail) throws ResourceNotFoundException {
		List<AppointMent> apps = appoinmentRepo.findByEmployeeEmailAndHospitalEmail(empEmail, hospEmail);
		if(!apps.isEmpty()) {
			return apps;	
			}
			throw new ResourceNotFoundException("Appointment", "Hospital-Email", "Employee Email :"+empEmail +" & hospital Email :"+ hospEmail);
	}
	@Override
	public List<AppointMent> deleteByHospAndEmp(String empEmail, String hospEmail) throws CustomExceptions {
		List<AppointMent> apps = appoinmentRepo.findByEmployeeEmailAndHospitalEmail(empEmail, hospEmail);
		deleteAppintments(apps);
		return apps;
	}

}
