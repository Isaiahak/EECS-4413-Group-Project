package com.example.BidlyPagesService.dto;

//Live Update DTO, sent from Live Server Service, used to update time and highest bid in real time
public class LiveUpdate {

    private Long aid;
    private String title;
    private String timeRemaining;
    private int highestBid;

    public LiveUpdate(){
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

    public String getTimeRemaining() {
        return timeRemaining;
    }

    public void setTimeRemaining(String timeRemaining) {
        this.timeRemaining = timeRemaining;
    }

    public int getHighestBid() {
        return highestBid;
    }

    public void setHighestBid(int highestBid) {
        this.highestBid = highestBid;
    }
}



