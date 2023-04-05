package com.empservice.employee.service;

import java.util.List;
import java.util.Optional;import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.empservice.employee.entity.Employee;
import com.empservice.employee.exceptions.CustomExceptions;
import com.empservice.employee.exceptions.DataAlreadyExistException;
import com.empservice.employee.exceptions.ResourceNotFoundException;
import com.empservice.employee.repo.EmployeeRepository;
import com.empservice.employee.utils.Address;
import com.empservice.employee.utils.EmployeeDto;
import com.empservice.employee.utils.Helper;
import com.empservice.employee.utils.User;

@Service
public class EmployeeServiceImpl implements EmployeeService{
	


	@Autowired EmployeeRepository empRepo;
	@Autowired ModelMapper mapper;
	
	private EmployeeDto ModelToDto(Employee employee) {
		EmployeeDto utils = new EmployeeDto();
		mapper.map(employee, utils);
		return utils;
	}
	
	private Employee dtoToModel(EmployeeDto empDto) {
		Employee emp = new Employee();
		mapper.map(empDto, emp);
		return emp;
	}
	@Override
	public EmployeeDto createEmployee(EmployeeDto empDto) {
		if(checkIfEmpAlreadyExist(empDto.getEmpEmail())==null) {
			try {
				empDto.setPassword(Helper.getEncryptedPassword(empDto.getPassword()));
				return ModelToDto(empRepo.save(dtoToModel(empDto)));
			} catch (Exception e) {
				throw new CustomExceptions(e.getMessage(),empDto);
			}
		}else
		throw new CustomExceptions("Employee Already Exist with the email : ", empDto.getEmpEmail());
	}
	private Employee checkIfEmpAlreadyExist(String empEmail) {
		Optional<Employee> empByEmail = empRepo.findByEmpEmail(empEmail);
		if(empByEmail.isPresent()) {
			return empByEmail.get();
		}
		return null;
	}

	@Override
	public EmployeeDto findEmpByEmail(String email) {
	   Employee byEmail = checkIfEmpAlreadyExist(email);
	   if(byEmail!=null) {
		   return ModelToDto(byEmail);
	   }
		throw new ResourceNotFoundException("Employee", "email", email);
	}

	@Override
	public List<EmployeeDto> findEmpsByOrgEmail(String orgEmail) {
		List<Employee> employees = empRepo.findByOrgEmail(orgEmail);
		if(employees!=null && !employees.isEmpty())
			return employees.stream().map(emp->ModelToDto(emp)).collect(Collectors.toList());
		throw new ResourceNotFoundException("employees", "organisation Email", orgEmail);
		
	}

	@Override
	public EmployeeDto deleteEmpByEmail(String email) {
		Employee emp = checkIfEmpAlreadyExist(email);
		if(emp!=null) {
			try {
				empRepo.delete(emp);
				return ModelToDto(emp);
			}catch (Exception e) {
              e.printStackTrace();
			}
		}
		else
		throw new ResourceNotFoundException("employee", "email", email);
		return null;
	}

	@Override
	public EmployeeDto updateOrgEmailByEmpEmail(String empEmail,String orgEmail) {
		Employee emp = checkIfEmpAlreadyExist(empEmail);
		if(emp!=null) {
			try {
				emp.setOrgEmail(orgEmail);
				return ModelToDto(empRepo.save(emp));
			}catch (Exception e) {
              e.printStackTrace();
			}
		}
		else
		throw new ResourceNotFoundException("employee", "email", empEmail);
		return null;
	}

	@Override
	public EmployeeDto updateAddressEmpByEmail(String email,Address address) {
		Employee emp = checkIfEmpAlreadyExist(email);
		if(emp!=null) {
			try {
				emp.setAddLine1(address.getAddLine1());
				emp.setCity(address.getCity());
				emp.setZip(address.toString());
				return ModelToDto(empRepo.save(emp));
			}catch (Exception e) {
              e.printStackTrace();
			}
		}
		else
		throw new ResourceNotFoundException("employee", "email", email);
		return null;
	}

	@Override
	public String getPasswordByEmail(String email) {
		Employee emp = checkIfEmpAlreadyExist(email);
		if(emp!=null) {
			return emp.getPassword();
		}
		return "Email Not Present";
	}

	@Override
	public EmployeeDto validateEmployee(User user) {
		Employee emp = checkIfEmpAlreadyExist(user.getEmail());
		if(emp!=null) {
			if(Helper.decryptPassword(emp.getPassword()).equals(user.getPassword())){
				return ModelToDto(emp);
			}else {
				throw new CustomExceptions("password is wrong for email : ", user.getEmail());
			}
			 
		}
		else throw new CustomExceptions("user not found for email : ", user.getEmail());
	}

}
