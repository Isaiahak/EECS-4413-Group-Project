package com.example.BidlyPagesService.webSocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.client.WebSocketClient;



//Websocket Client, to recieve real time updates from the Live server service.
@Component
public class AuctionWebSocketClient {

    //Listen to Live Server Websocket Server
    private static final String WEBSOCKET_URL = "ws://localhost:8086/live";

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
