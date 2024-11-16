package com.example.BidlyLiveServer.config;

import com.example.BidlyLiveServer.dto.LiveUpdate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import java.util.ArrayList;
@Configuration
public class WebConfig {
    @Bean
    public ArrayList<LiveUpdate> arrayList(){
        return new ArrayList<>();
    }
}
