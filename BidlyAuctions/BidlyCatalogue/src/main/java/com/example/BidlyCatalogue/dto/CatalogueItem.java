package com.example.BidlyCatalogue.dto;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

//Catalogue Item DTO, used to send to Live Server and Pages Microservices.
//Defines table structure for catalogue table in DB
//Used to perform DB operations.
@Entity
@Table(name = "catalogue")
public class CatalogueItem {

    @Id
    @Column(name = "aid")
    private long aid;

    @Column(name = "title")
    private String title;

    @Column(name = "highestBid")
    private int highestBid;

    @Column(name = "auctionTime")
    private String auctionTime;

    @Column(name = "initialPrice")
    private String initialPrice;

    @Column(name = "shippingDate")
    private String shippingDate;

    @Column(name = "expeditedShipping")
    private double expeditedShipping;

    @Column(name = "type")
    private String type;

    @Column(name = "shippingPrice")
    private String shippingPrice;

    @Column(name = "itemid")
    private long itemid;

    @Column(name = "reductionAmount")
    private int reductionAmount;

    @Column(name = "reductionInterval")
    private String reductionInterval;

    public long getAid() {
        return aid;
    }

    public void setAid(long aid) {
        this.aid = aid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getHighestBid () {
        return this.highestBid;
    }

    public void setHighestBid(int highestBid) {
        this.highestBid = highestBid;
    }

    public String getAuctionTime() {
        return auctionTime;
    }

    public void setAuctionTime(String auctionTime) {
        this.auctionTime = auctionTime;
    }

    public String getInitialPrice() {
        return initialPrice;
    }

    public void setInitialPrice(String initialPrice) {
        this.initialPrice = initialPrice;
    }

    public String getShippingDate() {
        return shippingDate;
    }

    public void setShippingDate(String shippingDate) {
        this.shippingDate = shippingDate;
    }

    public double getExpeditedShipping() {
        return expeditedShipping;
    }

    public void setExpeditedShipping(double expeditedShipping) {
        this.expeditedShipping = expeditedShipping;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getShippingPrice() {
        return shippingPrice;
    }

    public void setShippingPrice(String shippingPrice) {
        this.shippingPrice = shippingPrice;
    }

    public int getReductionAmount() {
        return reductionAmount;
    }

    public void setReductionAmount(int reductionAmount) {
        this.reductionAmount = reductionAmount;
    }

    public String getReductionInterval() {
        return reductionInterval;
    }

    public void setReductionInterval(String reductionInterval) {
        this.reductionInterval = reductionInterval;
    }

    public long getItemID() {
        return itemid;
    }

    public void setItemID(long itemID) {
        this.itemid = itemID;
    }
}
