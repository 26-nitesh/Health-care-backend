package com.service.agency.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.service.agency.entity.Agency;

public interface AgencyRepo extends JpaRepository<Agency, Integer> {

}
