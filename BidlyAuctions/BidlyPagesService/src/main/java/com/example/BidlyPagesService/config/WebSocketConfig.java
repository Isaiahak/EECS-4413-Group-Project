package com.example.BidlyPagesService.config;

import com.example.BidlyPagesService.webSocket.AuctionWebSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.WebSocketHandler;
import com.example.BidlyPagesService.webSocket.AuctionWebSocketClient;

@Configuration
public class WebSocketConfig {

    @Bean
    public WebSocketClient webSocketClient() {
        return new StandardWebSocketClient();  // Standard WebSocket client to connect to the WebSocket server
    }
    @Bean
    public WebSocketHandler webSocketHandler() {
        return new AuctionWebSocketHandler();  // Standard WebSocket client to connect to the WebSocket server
    }
}
