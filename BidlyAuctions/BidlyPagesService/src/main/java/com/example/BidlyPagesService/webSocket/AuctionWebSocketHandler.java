package com.example.BidlyPagesService.webSocket;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import java.util.List;
import com.example.BidlyPagesService.dto.Auction;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

@Component
public class AuctionWebSocketHandler implements WebSocketHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("WebSocket connection established");
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        // Parse the incoming message
//        String auctionJSON = message.getPayload();
//        List<Auction> auctions = objectMapper.readValue(auctionJSON, new TypeReference<List<Auction>>() {});

        // Handle the auction update (e.g., update your UI or call a service)
//        System.out.println("Received auction update: " + auctions);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        System.out.println("WebSocket error: " + exception.getMessage());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("WebSocket connection closed");
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
