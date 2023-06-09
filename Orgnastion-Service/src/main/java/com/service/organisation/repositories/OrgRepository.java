package com.service.organisation.repositories;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.service.organisation.entity.Organisation;

public interface OrgRepository extends JpaRepository<Organisation, Integer> {

	Optional<Organisation> findByOrganisationEmail(String email);
//	@Cacheable
	List<Organisation> findByInsuranceAgencyEmail(String email);
	
	@Transactional
	@Modifying
	@Query(value = "delete from organisation org where org.insurance_agency_email :email",nativeQuery = true)
	void deleteAllByInsuranceAgencyEmail(String email);
	
//	Optional<Organisation> findByOrganisationEmailAndPassword(String email,String password);
}
