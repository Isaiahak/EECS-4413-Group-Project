package com.example.BidlyPagesService.config;


import com.example.BidlyPagesService.webSocket.AuctionWebSocketHandler;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

@Configuration
public class WebSocketClientConfig  {

    @Bean
    public WebSocketClient webSocketClient(){
        return new StandardWebSocketClient();
    }

    @Bean
    public WebSocketHandler webSocketHandler(){
        return new AuctionWebSocketHandler();
    }
}
