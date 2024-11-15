package com.example.BidlyLiveServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BidlyLiveServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BidlyLiveServerApplication.class, args);
	}

}
