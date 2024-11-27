package com.example.BidlyPagesService.dto;

//Catalogue Item DTO, used to send and recieve catalogue information.
//This is mainly used to add or remove auctions from the webpage.
public class CatalogueItem {

    private long aid;

    private String title;

    private int highestBid;

    private String auctionTime;

    private String initialPrice;

    private String shippingDate;

    private double expeditedShipping;

    private String type;

    private double shippingPrice;


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

    public double getShippingPrice() {
        return shippingPrice;
    }

    public void setShippingPrice(double shippingPrice) {
        this.shippingPrice = shippingPrice;
    }
}
