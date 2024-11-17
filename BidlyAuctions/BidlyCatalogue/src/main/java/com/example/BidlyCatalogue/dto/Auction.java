package com.example.BidlyCatalogue.dto;

import jakarta.persistence.*;

//Auction DTO, received from Pages to create auctions.
//Defines table structure for auction table in DB
//Used to perform DB operations.
@Entity
@Table(name = "auction")
public class Auction {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long aid;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "\"desc\"")
    private String desc;

    @Column(name = "currentHighestBidder")
    private int highestBid;

    @Column(name = "type")
    private String type;

    @Column(name = "time_limit")
    private String timeRemaining;


    public Auction( String title, String desc, int highestBid, String type, String timeRemaining) {
        this.title = title;
        this.desc = desc;
        this.highestBid = highestBid;
        this.type = type;
        this.timeRemaining = timeRemaining;
    }
    public Auction(){

    }

    public Long getAid() {
        return aid;
    }

    public void setAid(Long aid) {
        this.aid = aid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getHighestBid() {
        return highestBid;
    }

    public void setHighestBid(int highestBid) {
        this.highestBid = highestBid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTimeRemaining() {
        return timeRemaining;
    }

    public void setTimeRemaining(String timeRemaining) {
        this.timeRemaining = timeRemaining;
    }
}