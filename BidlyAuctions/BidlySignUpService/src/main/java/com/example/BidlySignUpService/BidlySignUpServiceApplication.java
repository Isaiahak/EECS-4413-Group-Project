package com.example.BidlySignUpService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

//TODO: Rename to BidlyCredentialsService
//This is the Credentials Microservice. It contains the Credentials Handler. This service is responsible for all signup or login processes
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class BidlySignUpServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BidlySignUpServiceApplication.class, args);
	}

}
