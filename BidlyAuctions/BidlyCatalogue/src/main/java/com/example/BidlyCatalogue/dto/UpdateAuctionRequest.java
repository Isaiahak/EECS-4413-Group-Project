package com.example.BidlyCatalogue.dto;

//Update Auctions DTO, used to send to Live Server to notify of a new bid.
public class UpdateAuctionRequest {
    private Long aid;
    private int bid;

    public UpdateAuctionRequest(){
    }

    public Long getAid() {
        return aid;
    }

    public void setAid(Long aid) {
        this.aid = aid;
    }

    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }
}

