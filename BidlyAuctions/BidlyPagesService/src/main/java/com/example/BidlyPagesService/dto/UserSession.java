package com.example.BidlyPagesService.dto;

//User Session DTO. used internally by this microservice to track user sessions and subscriptions.
import org.springframework.web.socket.WebSocketSession;

public class UserSession {
    private String username;
    private WebSocketSession session;

    public UserSession() {
    }

    //THIS CODE IS HERE IN CASE WE SWITCH TO TRACKING USER ID INSTEAD OF USERNAME
//    public Long getUid() {
//        return uid;
//    }
//
//    public void setUid(Long uid) {
//        this.uid = uid;
//    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public WebSocketSession getSession() {
        return session;
    }

    public void setSession(WebSocketSession session) {
        this.session = session;
    }
}
