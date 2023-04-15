package com.policyservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
//@EnableDiscoveryClient
public class OrgInsurancePolicyServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrgInsurancePolicyServiceApplication.class, args);
	}
	@Bean
	public ObjectMapper objMApper() {
		return new ObjectMapper();
	}

}
