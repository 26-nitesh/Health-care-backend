package com.service.hospital.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.service.hospital.entity.Hospital;

public interface HospitalRepo extends JpaRepository<Hospital, Integer>{

	Optional<Hospital> findByHospitalEmail(String email);
	List<Hospital> findByAgencyEmail(String email);
//	  @Query("SELECT h FROM Hospital h WHERE :agency IN h.agencies")
	@Query("SELECT h FROM Hospital h WHERE :agency MEMBER OF h.agencies")
	    List<Hospital> findByAgency(@Param("agency") String agency);

}
