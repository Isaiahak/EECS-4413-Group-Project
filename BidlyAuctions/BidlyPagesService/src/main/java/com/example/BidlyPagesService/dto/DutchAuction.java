package com.example.BidlyPagesService.dto;

import com.fasterxml.jackson.annotation.JsonProperty;


//Auction DTO Used to create and send auction information to Catalogue Service
//NOTE: During development, the catalogue service was not able to read the AuctionDTO
//      Unless the JsonProperty tags were used. It seems to be working without it now
//      However, we will keep this here untill proper testing is done.
public class DutchAuction {
    @JsonProperty("aid")
    private Long aid;

    @JsonProperty("title")
    private String title;

    @JsonProperty("desc")
    private String desc;

    @JsonProperty("price")
    private int price;

    @JsonProperty("type")
    private String type;

    @JsonProperty("time-interval")
    private String timeRemaining;

    @JsonProperty("itemid")
    private long itemid;

    @JsonProperty("userid")
    private String userid;

    public int getReductionAmount() {
        return reductionAmount;
    }

    public void setReductionAmount(int reductionAmount) {
        this.reductionAmount = reductionAmount;
    }

    @JsonProperty("reductionAmount")
    private int reductionAmount;


    public DutchAuction(String title, String desc, int price, String type, String timeRemaining, int reductionAmount) {
        this.title = title;
        this.desc = desc;
        this.price = price;
        this.type = type;
        this.timeRemaining = timeRemaining;
        this.reductionAmount = reductionAmount;
    }

    public DutchAuction(){

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
        return price;
    }

    public void setHighestBid(int highestBid) {
        this.price = highestBid;
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

    public long getItemid() {
        return itemid;
    }

    public void setItemid(long itemid) {
        this.itemid = itemid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
