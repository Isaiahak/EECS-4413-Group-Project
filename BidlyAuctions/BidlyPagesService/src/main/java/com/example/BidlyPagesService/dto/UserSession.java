package com.example.BidlyPagesService.dto;

import org.springframework.web.socket.WebSocketSession;

public class UserSession {
    private String username;
    private WebSocketSession session;

    public UserSession() {
    }

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
