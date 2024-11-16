package com.example.BidlyPagesService.service;


import com.example.BidlyPagesService.dto.CatalogueItem;
import com.example.BidlyPagesService.dto.UserSession;
import jakarta.annotation.PostConstruct;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SubscriberService {

    private final Map<String, WebSocketSession> users = new ConcurrentHashMap<>();

    private final Map<Long, ArrayList<String>> subscriptions = new ConcurrentHashMap<>();


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

        for(String subscriber : auctionSubs){
            sessions.add(users.get(subscriber));
        }
         return sessions;
    }
}
