package com.example.BidlyPagesService.webSocket;

import com.example.BidlyPagesService.dto.CatalogueItem;
import com.example.BidlyPagesService.service.AuctionService;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import java.util.List;
import com.example.BidlyPagesService.dto.Auction;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

@Component
public class AuctionWebSocketHandler implements WebSocketHandler {

    @Autowired
    private AuctionService auctionService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("WebSocket connection established");
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        System.out.println(message.getPayload());
        String payload = message.getPayload().toString();
        System.out.println(payload);
        CollectionType ct = objectMapper.getTypeFactory().constructCollectionType(List.class, CatalogueItem.class);
        List<CatalogueItem> catalogueItems  = objectMapper.readValue(payload, ct);
        System.out.println("Updating Auctions");
        auctionService.updateAuctions(catalogueItems);
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
