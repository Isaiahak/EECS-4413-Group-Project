package com.example.BidlySignUpService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class BidlySignUpServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BidlySignUpServiceApplication.class, args);
	}

}
