package com.service.organisation;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
public class OrgnastionServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrgnastionServiceApplication.class, args);
	}

	@Bean
	public ObjectMapper objMApper() {
		return new ObjectMapper();
	}
}
