package com.service.policy.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.service.policy.entity.Policy;

public interface PolicyRepo extends JpaRepository<Policy, Integer> {
 List<Policy> findByOrgEmail(String email);
}
