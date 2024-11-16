package com.example.BidlyLiveServer.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

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
