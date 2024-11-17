package com.example.BidlyPagesService.dto;

//UpdateAuctionRequest, used to send auction bid updates to Catalogue Service
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
