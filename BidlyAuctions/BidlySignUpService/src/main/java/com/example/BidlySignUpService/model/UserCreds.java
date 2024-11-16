package com.example.BidlySignUpService.model;

import jakarta.persistence.*;


@Entity
@Table(name = "usercreds")
public class UserCreds {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uid;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "passhash", nullable = false)
    private String passhash;

    public Long getUid() {
        return uid;
    }

    public String getPassword() {
        return passhash;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.passhash = password;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
