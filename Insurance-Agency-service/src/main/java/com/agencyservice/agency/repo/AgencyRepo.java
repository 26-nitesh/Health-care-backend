package com.agencyservice.agency.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.agencyservice.agency.entity.Agency;

@Repository
public interface AgencyRepo extends JpaRepository<Agency, Integer> {

	Optional<Agency> findByAgencyEmail(String orgEmail);

}
