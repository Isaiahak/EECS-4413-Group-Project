package com.example.BidlyPagesService.service;

import com.example.BidlyPagesService.dto.UserSession;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//TODO: Handle user logout,
//TODO: Handle auction end unsubscribe: When an auction ends, user will be unsubscribed from that auction.

//This Service is solely responsible for tracking and providing subscriptions to auctions.
@Service
public class SubscriberService {

    private final Map<String, WebSocketSession> users = new ConcurrentHashMap<>();

    private final Map<Long, ArrayList<String>> subscriptions = new ConcurrentHashMap<>();


    //Create a user session to be stored for subscriptions
    /* NOTE (IMPORTANT)
    The reason this is here is because websocket sessions do not persist across pages
    This means that when a user navigates away from the catalogue page, where the websocket
    connection is established, the websocket is disconnected and is no longer available.
    Sending messages to a non existent web socket will halt the host (Pages Service) until it times out
    As a remedy, We use the User Session object to assign a websocket to a username. The username is unique,
    so we can guarantee that there will never be two websocket sessions with the same username.
    Therefore, when a user reconnects or starts a new websocket session, we can use the userID to identify which
    user has connected, and override the previous websocket which no longer exists. This way, no matter how many
    new websockets are made or how many times the user reconnects, the user will be able to recieve messages from
    auctions they have subscribed to.
     */
    public void createUserSession(UserSession userSession){
        if(users.containsKey(userSession.getUsername())){
            users.replace(userSession.getUsername(), userSession.getSession());
        }else {
            users.put(userSession.getUsername(), userSession.getSession());
            System.out.println(users.get(userSession.getUsername()));
        }
    }
    public void subscribe (Long aid, String username){
        if(subscriptions.containsKey(aid)){
            if(!subscriptions.get(aid).contains(username)){
                ArrayList<String> auctionSubs = subscriptions.get(aid);
                auctionSubs.add(username);
                subscriptions.put(aid, auctionSubs);
            }
        }else {
            ArrayList<String> auctionSubs = new ArrayList<>();
            auctionSubs.add(username);
            subscriptions.put(aid, auctionSubs);
        }
    }

    public ArrayList<WebSocketSession> endAuction(Long aid){
        ArrayList<WebSocketSession> sessions = new ArrayList<>();
        ArrayList<String> auctionSubs = subscriptions.get(aid);

        if(auctionSubs == null){
            return null;
        }
        for(String subscriber : auctionSubs){
            sessions.add(users.get(subscriber));
        }

         return sessions;
    }
}
