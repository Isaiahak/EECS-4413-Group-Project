package com.example.BidlyCatalogue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

//TODO: Rename to Backend
//This is the Catalogue Microservice, it is responsible for handling all business logic related to auctions.
//This Microservice contains the BiddingProcess Component.
@SpringBootApplication
@EnableScheduling
public class BidlyCatalogueApplication {

	public static void main(String[] args) {
		SpringApplication.run(BidlyCatalogueApplication.class, args);
	}

}
