package com.example.BidlyPagesService.dto;

public class Auction {
    private String id;
    private String title;
    private String desc;
    private int highestBid;
    private String type;
    private String timeRemaining;


    public Auction( String title, String desc, int highestBid, String type, String timeRemaining) {
        this.title = title;
        this.desc = desc;
        this.highestBid = highestBid;
        this.type = type;
        this.timeRemaining = timeRemaining;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
