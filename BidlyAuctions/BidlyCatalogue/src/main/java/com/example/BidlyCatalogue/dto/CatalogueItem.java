package com.example.BidlyCatalogue.dto;


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

    private String title;
    private int current_highest_bid;
    private String time_remaining;

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

    public int getCurrent_highest_bid(int highestBid) {
        return current_highest_bid;
    }

    public void setCurrent_highest_bid(int current_highest_bid) {
        this.current_highest_bid = current_highest_bid;
    }

    public String getTime_remaining() {
        return time_remaining;
    }

    public void setTime_remaining(String time_remaining) {
        this.time_remaining = time_remaining;
    }
}
