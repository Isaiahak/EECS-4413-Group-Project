package com.example.BidlyPagesService.webSocket;

import com.example.BidlyPagesService.api.ApiService;
import com.example.BidlyPagesService.dto.*;
import com.example.BidlyPagesService.service.AuctionService;
import com.example.BidlyPagesService.service.SubscriberService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class AuctionWebSocketHandler extends TextWebSocketHandler implements WebSocketHandler{

    @Autowired
    private List<WebSocketSession> sessions = new ArrayList<>();

    @Autowired
    private ApiService apiService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private SubscriberService subscriberService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception{
        System.out.println(session.getLocalAddress().getPort());
        if(session.getLocalAddress().getPort() != 8086) {

            sessions.add(session);
        }

        System.out.println("WebSocket Connection Established");

        List<CatalogueItem> catalogue = initCatalogue(); // Non-blocking if async
        String catalogueJSON = buildMessage("init", catalogue);
        session.sendMessage(new TextMessage(catalogueJSON));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception{
        sessions.remove(session);
        System.out.println("WebSocket Connection Closed");
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        System.out.println("Received message in UI component: " + message.getPayload());
            // Parse the incoming message to get the type
            JsonNode jsonNode = objectMapper.readTree((String)message.getPayload());
            String type = jsonNode.get("type").asText();

            if(jsonNode.get("type").asText().equals("connect")){
                UserSession userSession = new UserSession();
                String username = objectMapper.convertValue(jsonNode.get("data"), String.class);
                userSession.setUsername(username);
                userSession.setSession(session);
                subscriberService.createUserSession(userSession);
            }else if(jsonNode.get("type").asText().equals("closed")) {
                Long aid = objectMapper.convertValue(
                        jsonNode.get("data"),
                        Long.class
                );
                ArrayList<WebSocketSession> auctionSubs = subscriberService.endAuction(aid);
                String formattedClosed = buildMessage("closed", aid);

                for (WebSocketSession client : auctionSubs) {
                    System.out.println(client.getId() + "," + sessions.size());
                    client.sendMessage(new TextMessage(formattedClosed));
                }

            }else{
                // Get the data array and convert it
                ArrayList<LiveUpdate> updates = objectMapper.convertValue(
                        jsonNode.get("data"),
                        new TypeReference<ArrayList<LiveUpdate>>() {
                        }
                );
                String formattedUpdate = buildMessage("update", updates);
                System.out.println(formattedUpdate);
                // Send to all connected clients
                System.out.println(sessions.size());
                for (WebSocketSession client : sessions) {
                    System.out.println(client.getId() + "," + sessions.size());
                    client.sendMessage(new TextMessage(formattedUpdate));
                }
            }
    }

    // Remove the separate update method since we're handling it in handleMessage
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        System.out.println("WebSocket error: " + exception.getMessage());
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
    public List<CatalogueItem> initCatalogue()throws Exception{
        return apiService.callCatalogueGetCatalogue();

    }


    public void addCatalogueItem(CatalogueItem catalogueItem) throws IOException {
        String catalogueItemJSON = buildMessage("newCat", catalogueItem);
        for(WebSocketSession session : sessions){
            session.sendMessage(new TextMessage(catalogueItemJSON));
        }
    }

    private String buildMessage(String type, Object data){
        try{
            ObjectNode message = objectMapper.createObjectNode();
            message.put("type", type);
            message.set("data", objectMapper.valueToTree(data));
            return objectMapper.writeValueAsString(message);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    public void update(String payload) throws IOException {
//        //System.out.println(payload);
////        ArrayList<LiveUpdate> updatesList = objectMapper.readValue(payload, new TypeReference<ArrayList<LiveUpdate>>() {});
////        String updates = buildMessage("update", s);
//        for(WebSocketSession client : sessions){
//            client.sendMessage(new TextMessage(s));
//        }
    }
}
