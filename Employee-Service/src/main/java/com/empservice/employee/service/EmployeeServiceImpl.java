package com.empservice.employee.service;

import java.time.LocalDate;
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
	
//	@Override
//	public  EmployeeDto ModelToDto(Employee employee) {
////		EmployeeDto utils = new EmployeeDto();
////		System.out.println("comming "+employee.getEmpEmail());
//	return	mapper.map(employee, EmployeeDto.class);
////		System.out.println("coming");
////		return utils;
//	}
//	@Override
//	public Employee dtoToModel(EmployeeDto empDto) {
////		Employee emp = new Employee();
//		return mapper.map(empDto, Employee.class);
////		return emp;
//	}
	@Override
	public Employee createEmployee(Employee employee)  throws CustomExceptions, ResourceNotFoundException{
		if(checkIfEmpAlreadyExistWithOrg(employee.getEmpEmail(),employee.getOrgEmail())==null) {
			try {
//				System.out.println(empRepo.save(dtoToModel(empDto)).getEmpEmail());
				employee.setPassword(Helper.getEncryptedPassword(employee.getPassword()));
//				System.out.println(employee.getEmpEmail());
				return empRepo.save(employee);
			} catch (Exception e) {
//				e.printStackTrace();
				throw new CustomExceptions("exception occured while saving employee with email : ",employee.getEmpEmail());
			}
		}else
		throw new CustomExceptions("Employee Already Exist with the email : ", employee.getEmpEmail());
	}
	
	private Employee checkIfEmpAlreadyExist(String empEmail) throws ResourceNotFoundException {
		if(empEmail==null || empEmail.isEmpty())
			throw new ResourceNotFoundException("email is not valid");
		Optional<Employee> empByEmail = empRepo.findByEmpEmail(empEmail);
		if(empByEmail.isPresent()) {
			return empByEmail.get();
		}
		return null;
	}
	private Employee checkIfEmpAlreadyExistWithOrg(String empEmail,String orgEmail) throws ResourceNotFoundException {
		if(empEmail==null || empEmail.isEmpty())
			throw new ResourceNotFoundException("email is not valid");
		Optional<Employee> empByEmail = empRepo.findByEmpEmailAndOrgEmail(empEmail, orgEmail);
		if(empByEmail.isPresent()) {
			return empByEmail.get();
		}
		return null;
	}

	@Override
	public Employee findEmpByEmail(String email) throws ResourceNotFoundException {
	   Employee byEmail = checkIfEmpAlreadyExist(email);
	   if(byEmail!=null) {
		   return byEmail;
	   }
		throw new ResourceNotFoundException("Employee", "email", email);
	}

	@Override
	public List<Employee> findEmpsByOrgEmail(String orgEmail) throws ResourceNotFoundException {
		List<Employee> employees = empRepo.findByOrgEmail(orgEmail);
		if(employees!=null && !employees.isEmpty())
           return employees;
			throw new ResourceNotFoundException("employees", "organisation Email", orgEmail);
		
	}

	@Override
	public Employee deleteEmpByEmail(String email) throws ResourceNotFoundException, CustomExceptions{
		Employee emp = checkIfEmpAlreadyExist(email);
		if(emp!=null) {
			try {
				empRepo.delete(emp);
				return emp;
			}catch (Exception e) {
              e.printStackTrace();
              throw new CustomExceptions("Exception occured while deleting  ", e.getMessage());
			}
		}
		throw new ResourceNotFoundException("employee", "email", email);
//		return null;
	}

	@Override
	public Employee updateOrgEmailByEmpEmail(String empEmail,String orgEmail) throws CustomExceptions ,ResourceNotFoundException {
		Employee emp = checkIfEmpAlreadyExist(empEmail);
		if(emp!=null) {
			try {
				emp.setOrgEmail(orgEmail);
				return empRepo.save(emp);
			}catch (Exception e) {
              e.printStackTrace();
              throw new CustomExceptions("Exception occured while updating ", e.getMessage());
			}
		}
		else
		throw new ResourceNotFoundException("employee", "email", empEmail);
	}

	@Override
	public Employee updateAddressEmpByEmail(String email,Address address) throws ResourceNotFoundException, CustomExceptions {
		Employee emp = checkIfEmpAlreadyExist(email);
		if(emp!=null) {
			try {
				emp.setAddLine1(address.getAddLine1());
				emp.setCity(address.getCity());
				emp.setZip(address.getZip());
				return empRepo.save(emp);
			}catch (Exception e) {
              e.printStackTrace();
              throw new CustomExceptions("exception occured while updating address for employee :" , email);
			}
		}
		throw new ResourceNotFoundException("employee", "email", email);
	}

	@Override
	public Employee updateEmployee(Employee employee) throws ResourceNotFoundException, CustomExceptions {
 
		Employee empFound = checkIfEmpAlreadyExist(employee.getEmpEmail());
		if(!validatePassword(empFound, employee))
			throw new CustomExceptions("New Password and old Password did not match");
		if(empFound!=null) {
			if(employee.getAddLine1()!=null && !employee.getAddLine1().isEmpty())
			empFound.setAddLine1(employee.getAddLine1());
			
			if(employee.getCity()!=null && !employee.getCity().isEmpty())
			empFound.setCity(employee.getCity());
			
			if(employee.getZip()!=null && !employee.getZip().isEmpty())
			empFound.setZip(employee.getZip());
			
			if(employee.getDob()!=null)
			empFound.setDob(employee.getDob());
			
			if(employee.getEmpName()!=null && !employee.getEmpName().isEmpty())
			empFound.setEmpName(employee.getEmpName());
			
			if(employee.getDateOfJoining()!=null)
				empFound.setDateOfJoining(employee.getDateOfJoining());
			
			if(employee.getEmpDesignation()!=null && !employee.getEmpDesignation().isEmpty())
				empFound.setEmpDesignation(employee.getEmpDesignation());
			
			if(employee.getEmpEmail()!=null && !employee.getEmpEmail().isEmpty())
				empFound.setEmpDesignation(employee.getEmpEmail());
			
			if(employee.getOrgEmail()!=null && !employee.getOrgEmail().isEmpty())
				empFound.setOrgEmail(employee.getOrgEmail());
			
		
//			empFound.setPassword(Helper.getEncryptedPassword(employee.getPassword()));
//			empFound.setEmpDesignation(employee.getEmpDesignation());
			return empRepo.save(empFound);
		}
		else 
			throw new ResourceNotFoundException("Employee", "email", employee.getEmpEmail());
	
	}

	public boolean validatePassword(Employee oldEmp, Employee newEmp) {
		
		return Helper.
				decryptPassword(
						oldEmp.getPassword()).equals(newEmp.getPassword());
	}

	@Override
	public Employee changePassword(User user) throws ResourceNotFoundException, CustomExceptions {
		Employee emp = checkIfEmpAlreadyExist(user.getEmail());
		if(emp!=null) {
			if(user.getNewPassword()==null || user.getNewPassword().isEmpty())
				throw new  CustomExceptions("New Password not valid");
			if(user.getPassword()!=null) {
				if(Helper.decryptPassword(emp.getPassword()).equals(user.getPassword())) {
					emp.setPassword(Helper.getEncryptedPassword(user.getNewPassword()));
					return  empRepo.save(emp);
				}else {
					throw new  CustomExceptions("Password did not match");
				}
			}
			throw new  CustomExceptions("Password not valid");
		}
		else
			throw new ResourceNotFoundException("Employee", "email", user.getEmail());
	}

	@Override
	public Object validateUserAndGetToken(User user) throws ResourceNotFoundException {
		Employee byEmail = checkIfEmpAlreadyExist(user.getEmail());
		if(byEmail!=null) {
			if(user.getPassword()==null && user.getPassword().isEmpty()) {
				throw new ResourceNotFoundException("password not present");
			}
			else if(Helper.decryptPassword(byEmail.getPassword()).equals(user.getPassword())) {
				return Helper.genrateJwtTokken(user);
			}else {
				throw new ResourceNotFoundException("wrong password");
			}
		}
		else
			throw new ResourceNotFoundException("Employee", "email", user.getEmail());
	}

	@Override
	public Object updateLastAppDateEmpEmail(String email, LocalDate lastAppDate)
			throws ResourceNotFoundException, CustomExceptions {
		Employee emp = checkIfEmpAlreadyExist(email);
//		System.out.println("##@#@ "+emp.getEmpEmail());
		if(emp!=null) {
			emp.setCheckupCount(emp.getCheckupCount()+1);
			 emp.setLastCheckupDate(lastAppDate);
		return	 empRepo.save(emp);
		}
		else
		throw new ResourceNotFoundException("Employee", "email", email);
	}


//	@Override
//	public String getPasswordByEmail(String email) {
//		Employee emp = checkIfEmpAlreadyExist(email);
//		if(emp!=null) {
//			return emp.getPassword();
//		}
//		return "Email Not Present";
//	}
//
//	@Override
//	public EmployeeDto validateEmployee(User user) {
//		Employee emp = checkIfEmpAlreadyExist(user.getEmail());
//		if(emp!=null) {
//			if(Helper.decryptPassword(emp.getPassword()).equals(user.getPassword())){
//				return ModelToDto(emp);
//			}else {
//				throw new CustomExceptions("password is wrong for email : ", user.getEmail());
//			}
//			 
//		}
//		else throw new CustomExceptions("user not found for email : ", user.getEmail());
//	}

}
