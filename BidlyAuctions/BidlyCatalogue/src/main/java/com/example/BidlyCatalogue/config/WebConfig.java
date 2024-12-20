package com.example.BidlyCatalogue.config;

import com.example.BidlyCatalogue.dto.Auction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;

@Configuration
public class WebConfig {
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean
    public Auction auction(){return new Auction();}
}
