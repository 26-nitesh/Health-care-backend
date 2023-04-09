package com.agencyservice.agency;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
public class InsuranceAgencyServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InsuranceAgencyServiceApplication.class, args);
	}

}
