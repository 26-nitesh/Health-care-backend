package com.service.organisation.services;

import java.util.List;

import com.service.organisation.entity.Organisation;

public interface OrgService {

	Organisation createOrg(Organisation organisation);

	Organisation getByEmail(String email);

	List<Organisation> getByAgencyEmail(String email);

	List<Organisation> deleteByAgencyEmail(String email);

	List<Integer> findPolicyIdsByEmail(String email);

	Organisation deleteByEmail(String email);

}
