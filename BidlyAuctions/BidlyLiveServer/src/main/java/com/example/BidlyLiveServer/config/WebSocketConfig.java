package com.example.BidlyLiveServer.config;


import com.example.BidlyLiveServer.websocket.LiveServerWebSocketHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    private final LiveServerWebSocketHandler liveServerWebSocketHandler;

    public WebSocketConfig(LiveServerWebSocketHandler liveServerWebSocketHandler){
        this.liveServerWebSocketHandler = liveServerWebSocketHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry){
        registry.addHandler(liveServerWebSocketHandler, "/live").setAllowedOrigins(("*"));
    }
}
