package com.service.organisation;


import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.*;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.SignatureAlgorithm;

@SpringBootApplication
//@EnableDiscoveryClient
public class OrgnastionServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrgnastionServiceApplication.class, args);
	}

	@Bean
	public ObjectMapper objMApper() {
		return new ObjectMapper();
	}
	   private String secret = "your-secret-key";
	    

}
