package com.service.appointment.service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.service.appointment.entity.AppointMent;
import com.service.appointment.exceptions.CustomExceptions;
import com.service.appointment.exceptions.ResourceNotFoundException;
import com.service.appointment.repo.AppointMentRepo;
import com.service.appointment.utils.UpdateAppointmentO;

@Service
public class AppointmentServiceimpl implements AppointmentService{

	@Autowired AppointMentRepo appoinmentRepo;
	@Override
	public AppointMent createnewAppointMent(AppointMent appointment) throws CustomExceptions {
		 Optional<AppointMent> appO = appoinmentRepo.findByEmployeeEmailAndIsArchived(appointment.getEmployeeEmail(), false);
          if(appO.isPresent())
        	  throw new CustomExceptions("Appointment already Exist", "");
		try {
			appointment.setAppintmentId(getUniqueAppId());
			appointment.setIsArchived(false);
			appointment.setStatus("pending");
			appointment.setBookingDate(LocalDate.now());
		    return	appoinmentRepo.save(appointment);
		}catch (Exception e) {
			throw new CustomExceptions("exception occured while creating appointment  :",e.getMessage());
		}
	}
	private int getUniqueAppId() {
		int appId = Math.absExact((int) (Math.random()*10000));
	   Optional<AppointMent> findById = appoinmentRepo.findById(appId);
	   if(findById.isPresent())
		 return  getUniqueAppId();
	   else
		return appId;
	}
	@Override
	public List<AppointMent> findByEmpEmail(String email) throws ResourceNotFoundException {
		List<AppointMent> apps = appoinmentRepo.findByEmployeeEmailOrderByBookingDateDesc(email);
		if(!apps.isEmpty()) {
		return apps;	
		}
		throw new ResourceNotFoundException("Appointment", "Employee-Email", email);
		
	}
	@Override
	public List<AppointMent> findByHospEmail(String email,boolean flag) throws ResourceNotFoundException {
		List<AppointMent> apps = appoinmentRepo.findByHospitalEmailAndIsArchived(email, flag);
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
	@Override
	public AppointMent updateAppintment(AppointMent appointment) throws ResourceNotFoundException {
		 Optional<AppointMent> appO = appoinmentRepo.findByEmployeeEmailAndIsArchived(appointment.getEmployeeEmail(), appointment.getIsArchived());
		
		 if(appO.isPresent()) {
			 AppointMent app = appO.get();
			 if(appointment.getStatus()!=null && !appointment.getStatus().isEmpty())
			  app.setStatus(appointment.getStatus());
			 if(!appointment.isVerified() || appointment.getStatus().equalsIgnoreCase("rejected"))
				 app.setIsArchived(true);
			 else
			  app.setIsArchived(appointment.getIsArchived());
			  app.setVerified(appointment.isVerified());
			  if(appointment.getRemarks()!=null && !appointment.getRemarks().isEmpty())
			  app.setRemarks(appointment.getRemarks());
			  return appoinmentRepo.save(app);
		 }
		 throw new ResourceNotFoundException("AppointMent not found");
	}
	@Override
	public AppointMent findByEmpAndDate(String empEmail, LocalDate date) throws CustomExceptions {
		 Optional<AppointMent> appO = appoinmentRepo.findByEmployeeEmailAndAppointmentDate(empEmail, date);
          if(appO.isPresent()) {
        	  return appO.get();
          }else
        	  throw new CustomExceptions("appointment not found","");
	}
	@Override
	public AppointMent updateStatus(int id, String status,Double amount) throws ResourceNotFoundException {
		 Optional<AppointMent> appO = appoinmentRepo.findByAppintmentId(id);
			System.out.println(amount);
		 if(appO.isPresent()) {
			 AppointMent app = appO.get();
			app.setStatus(status);
			if(amount!=null
					&&amount!=0)
				app.setAmount(amount);
			  return appoinmentRepo.save(app);
		 }
		 throw new ResourceNotFoundException("AppointMent not found");
	}
	@Override
	public List<AppointMent> findAllApps() throws ResourceNotFoundException {
		return appoinmentRepo.findAll();
	}

}
