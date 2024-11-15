package com.example.BidlyPagesService.config;

import com.example.BidlyPagesService.dto.CatalogueItem;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class WebConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public List<WebSocketSession> webSocketSessionList(){
        return new ArrayList<>();
    }
}