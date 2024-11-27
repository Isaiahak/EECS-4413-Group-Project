package com.example.BidlyPagesService.webSocket;

import com.example.BidlyPagesService.api.ApiService;
import com.example.BidlyPagesService.dto.*;
import com.example.BidlyPagesService.service.SubscriberService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.web.socket.handler.TextWebSocketHandler;

//Auction Websocket Handler, responsible for handling connection from clients and messages from live server
//This handler acts as a client to the live server, and a server to the users who will recieve information about updates.
//This handler exists to provide real time updates to clients (users) while decoupling the Live server from Users.
@Component
public class AuctionWebSocketHandler extends TextWebSocketHandler implements WebSocketHandler{

    //This arraylist of websocket sessions exists solely for the purpose of real time auction countdown and price changes.
    @Autowired
    private List<WebSocketSession> sessions = new ArrayList<>();

    @Autowired
    private ApiService apiService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private SubscriberService subscriberService;

    //After connection is established, we want to build the current list of auctions available.
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception{

        /* IMPORTANT
        Due to the fact that this handler acts as a client and a server (Chained conncetion)
        when the client connects to the live server, this method will run. This means that we
        need to prevent the live servers websocket session from being added to the list of
        active CLIENT sessions. If this websocket tries to send any messages to the live server
        which is not conifgured to handle any messages, this websocket service will halt until
        the conncetion times out.
         */
        System.out.println(session.getLocalAddress().getPort());
        if(session.getLocalAddress().getPort() != 8086) {
            sessions.add(session);
        }

        System.out.println("WebSocket Connection Established");

        //Initialize the current list of all active auctions.
        List<CatalogueItem> catalogue = initCatalogue();
        String catalogueJSON = buildMessage("init", catalogue);
        session.sendMessage(new TextMessage(catalogueJSON));
    }

    //After the conncetion is closed from the websocket, remove the session from the current list of active sessions.
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception{
        sessions.remove(session);
        System.out.println("WebSocket Connection Closed");
    }

    //This method is the only way Spring Websockets can accept messages from servers.
    //TODO: This method works for now, but for cleaner code, we will put the logic in separate methods
    //TODO: We leave this here for now as it may break, so we will test this properly after moving to separate methods.
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        System.out.println("Received message in UI component: " + message.getPayload());
            // Parse the message to get the type
            JsonNode jsonNode = objectMapper.readTree((String)message.getPayload());
            String type = jsonNode.get("type").asText();

            //If type is connect, create a usersession object for session "Peristence"
            if(jsonNode.get("type").asText().equals("connect")){
                UserSession userSession = new UserSession();
                String username = objectMapper.convertValue(jsonNode.get("data"), String.class);
                userSession.setUsername(username);
                userSession.setSession(session);
                subscriberService.createUserSession(userSession);
            }
            //If type is closed, request all subsribers for that auction and send them the alert
            else if(jsonNode.get("type").asText().equals("closed")) {
                Long aid = objectMapper.convertValue(jsonNode.get("data"), Long.class);
                String redirectUrl = objectMapper.convertValue(jsonNode.get("redirectUrl"), String.class);
                ArrayList<WebSocketSession> auctionSubs = subscriberService.endAuction(aid);
                //if no one is subscribed to the auction, do nothing.
                if(auctionSubs.isEmpty()){
                    return;
                }
                String formattedClosed = buildMessageWithRedirect("closed", aid, redirectUrl);
                for (WebSocketSession client : auctionSubs) {
                    // auctionsubs are null
                    client.sendMessage(new TextMessage(formattedClosed));
                }
            }
            else {
                //If the previous cases dont match, then the we process the live update
                ArrayList<LiveUpdate> updates = objectMapper.convertValue(jsonNode.get("data"), new TypeReference<ArrayList<LiveUpdate>>() {});
                String formattedUpdate = buildMessage("update", updates);
                for (WebSocketSession client : sessions) {
                    client.sendMessage(new TextMessage(formattedUpdate));
                }
            }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        System.out.println("WebSocket error: " + exception.getMessage());
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    //To initialize the current auction list, we make a REST call to catalogue service to fetch it.
    public List<CatalogueItem> initCatalogue()throws Exception{
        return apiService.callCatalogueGetCatalogue();

    }

    //when an auction is created, we use this method to send the new auction to all clients
    public void addCatalogueItem(CatalogueItem catalogueItem) throws IOException {
        String catalogueItemJSON = buildMessage("newCat", catalogueItem);
        for(WebSocketSession session : sessions){
            session.sendMessage(new TextMessage(catalogueItemJSON));
        }
    }

    //Helper method to create messages in a format the clients will understand
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

    private String buildMessageWithRedirect(String type, Object data, String redirect){
        try{
            ObjectNode message = objectMapper.createObjectNode();
            message.put("type", type);
            message.set("data", objectMapper.valueToTree(data));
            message.put("redirectUrl",redirect);
            return objectMapper.writeValueAsString(message);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


}
