package com.example.BidlyCatalogue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BidlyCatalogueApplication {

	public static void main(String[] args) {
		SpringApplication.run(BidlyCatalogueApplication.class, args);
	}

}
