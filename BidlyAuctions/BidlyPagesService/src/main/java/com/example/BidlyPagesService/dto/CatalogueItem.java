package com.example.BidlyPagesService.dto;

//Catalogue Item DTO, used to send and recieve catalogue information.
//This is mainly used to add or remove auctions from the webpage.
public class CatalogueItem {

    private long aid;

    private String title;
    private int highestBid;
    private String auctionTime;

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
}
