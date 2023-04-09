package com.service.hospital.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.service.hospital.entity.Hospital;

public interface HospitalRepo extends JpaRepository<Hospital, Integer>{

	Optional<Hospital> findByHospitalEmail(String email);
	List<Hospital> findByAgencyEmail(String email);

}
