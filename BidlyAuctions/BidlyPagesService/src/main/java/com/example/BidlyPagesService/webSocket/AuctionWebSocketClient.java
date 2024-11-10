package com.example.BidlyPagesService.webSocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.client.WebSocketClient;

@Component
public class AuctionWebSocketClient {

    private static final String WEBSOCKET_URL = "ws://localhost:8084/auction-updates";  // URL of your WebSocket server (change to correct server's port)

    private final WebSocketClient webSocketClient;
    private final WebSocketHandler webSocketHandler;

    @Autowired
    public AuctionWebSocketClient(WebSocketClient webSocketClient, WebSocketHandler webSocketHandler) {
        this.webSocketClient = webSocketClient;
        this.webSocketHandler = webSocketHandler;
        this.connect();
    }

    // Connect to the WebSocket server
    public void connect() {
        webSocketClient.execute(webSocketHandler, WEBSOCKET_URL);
    }
}
