package com.example.BidlyCatalogue.websocket;

import com.example.BidlyCatalogue.dto.Auction;
import com.example.BidlyCatalogue.dto.CatalogueItem;
import com.example.BidlyCatalogue.service.CatalogueService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;

@Component
public class AuctionWebSocketHandler extends TextWebSocketHandler {

    private final List<WebSocketSession> sessions = new ArrayList<>();


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception{
        sessions.add(session);
        System.out.println("WebSocket Connection Established");

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception{
        sessions.remove(session);
        System.out.println("WebSocket Connection Closed");
    }

    public void sendAuctionUpdate(List<CatalogueItem> catalogueItems)throws Exception{
        String auctionsJSON = new ObjectMapper().writeValueAsString((catalogueItems));
        System.out.println("Searching for sessions");
        for(WebSocketSession session: sessions){
            System.out.println("Session Found");
            if(session.isOpen()){
                System.out.println("Session is open");
                session.sendMessage((new TextMessage(auctionsJSON)));
            }
        }
    }
}
