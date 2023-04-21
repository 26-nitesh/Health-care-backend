package com.empservice.employee.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.empservice.employee.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer>{

	Optional<Employee> findByEmpEmail(String empEmail);//orgEmail
	List<Employee> findByOrgEmail(String orgEmail);
	Optional<Employee> findByEmpEmailAndOrgEmail(String empEmail,String orgEmail);
}
