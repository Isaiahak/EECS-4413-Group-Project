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
//import com.example.BidlyPagesService.webSocket.AuctionWebSocketClient;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final AuctionWebSocketHandler auctionWebSocketHandler;

    public WebSocketConfig(AuctionWebSocketHandler auctionWebSocketHandler) {
        this.auctionWebSocketHandler = auctionWebSocketHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(auctionWebSocketHandler, "/auction-updates")
                .setAllowedOrigins("*");  // Allow all origins, or specify if needed
    }
}
