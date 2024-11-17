package com.example.BidlyPagesService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//This is the Pages microservice. It contains the following components: Pages, Controller, SubscribeModule, and the PagesWebsocket.
/*
Since BidlyAuctions is built using springboot, the spring controller and bind.annotations act as the middleware controller and JSON parser
defined in the system architecture. So in short. this microservice contains the UI Layer and the Middleware Layer (Excluding security)
Springboot uses an MVC design for all its projects, however, the Spring controller, in this microservice acts as the main controller, as it
is responsible for mapping all the pages in this webapp.
 */
@SpringBootApplication
public class PagesApplication {

	public static void main(String[] args) {
		SpringApplication.run(PagesApplication.class, args);
	}

}
