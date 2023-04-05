package com.service.organisation.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.service.organisation.entity.Organisation;

public interface OrgRepository extends JpaRepository<Organisation, Integer> {

	Optional<Organisation> findByOrganisationEmail(String email);
}
