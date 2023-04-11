package com.policyservice.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.policyservice.entity.Policy;

@Repository
@EnableJpaRepositories
public interface PolicyRepo extends JpaRepository<Policy, Integer> {
 List<Policy> findByOrgEmail(String email);
 Optional<Policy> findByOrgEmailAndPolicyName(String email,String policyName);
}
