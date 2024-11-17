package com.example.BidlySecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//This is the Security Microservice, it is responsible for performing all security checks for the webapp
//As of now, this service only performs an SQL Injection check for all user inputs.
@SpringBootApplication
public class BidlySecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(BidlySecurityApplication.class, args);
	}

}
