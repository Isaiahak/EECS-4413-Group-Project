package com.example.BidlyLiveServer.websocket;


import com.example.BidlyLiveServer.dto.LiveUpdate;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class LiveServerWebSocketHandler extends TextWebSocketHandler {

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

    public void sendAuctionUpdate(List<LiveUpdate> updates)throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode messageWrapper = mapper.createObjectNode();
        messageWrapper.put("type", "update");
        messageWrapper.set("data", mapper.valueToTree(updates));

        String auctionsJSON = mapper.writeValueAsString(messageWrapper);
        System.out.println("Sending message: " + auctionsJSON);

        for(WebSocketSession session: sessions){
            if (session.isOpen()) {
                System.out.println("Session is open, sending message.");
                session.sendMessage(new TextMessage(auctionsJSON));
            } else {
                System.out.println("Session is not open. Removing from session list.");
                sessions.remove(session);
            }
        }
    }
    public void sendAuctionClosed(Long aid) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode messageWrapper = mapper.createObjectNode();
        messageWrapper.put("type", "closed");
        messageWrapper.put("data", aid);
        messageWrapper.put("redirectUrl","/pay-now");

        String auctionsJSON = mapper.writeValueAsString(messageWrapper);
        System.out.println("Sending message: " + auctionsJSON);

        for(WebSocketSession session: sessions){
            if (session.isOpen()) {
                System.out.println("Session is open, sending message.");
                session.sendMessage(new TextMessage(auctionsJSON));
            } else {
                System.out.println("Session is not open. Removing from session list.");
                sessions.remove(session);
            }
        }
    }
}
