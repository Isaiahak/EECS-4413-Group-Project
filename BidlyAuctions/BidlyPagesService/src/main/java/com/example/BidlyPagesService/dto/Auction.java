package com.example.BidlyPagesService.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Auction {
    @JsonProperty("aid")
    private Long aid;

    @JsonProperty("title")
    private String title;

    @JsonProperty("desc")
    private String desc;

    @JsonProperty("highestBid")
    private int highestBid;

    @JsonProperty("type")
    private String type;

    @JsonProperty("timeRemaining")
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
