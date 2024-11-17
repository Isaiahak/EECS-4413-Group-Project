package com.example.BidlyPagesService.config;

import com.example.BidlyPagesService.webSocket.AuctionWebSocketHandler;
import org.springframework.context.annotation.Configuration;
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

    //Register "auction-updates" end point
    //Clients (Users) will listen to this websocket
    //Websocket resides in this microservice, to prevent client access to backend
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(auctionWebSocketHandler, "/auction-updates")
                .setAllowedOrigins("*");  // Allow all origins, or specify if needed
    }
}
