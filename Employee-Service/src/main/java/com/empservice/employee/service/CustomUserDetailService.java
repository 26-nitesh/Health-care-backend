//package com.empservice.employee.service;
//
//import java.util.ArrayList;
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import com.empservice.employee.entity.Employee;
//import com.empservice.employee.repo.EmployeeRepository;
//import com.empservice.employee.utils.Helper;
//@Service
//public class CustomUserDetailService implements UserDetailsService  {
//
//	@Autowired private EmployeeRepository empRepo;
//	@Override
//	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//		Optional<Employee> employee = empRepo.findByEmpEmail(email);
//		if(employee.isPresent()) {
//			return new User(employee.get().getEmpEmail(), Helper.decryptPassword(employee.get().getPassword()), new ArrayList<>());
//		}
//		return null;
//	}
//
//}
