package com.example.BidlyPagesService.dto;

<<<<<<< HEAD
//Live Update DTO, sent from Live Server Service, used to update time and highest bid in real time
=======
>>>>>>> 5038a01 (added the shipping date)
public class LiveUpdate {

    private Long aid;
    private String title;
    private String timeRemaining;
    private int highestBid;
<<<<<<< HEAD
=======
    private String reductionInterval;
    private String reductionIntervalStore;
    private int reductionAmount;
    private int priceFloor;
    private String type;

>>>>>>> 5038a01 (added the shipping date)

    public LiveUpdate(){
    }

<<<<<<< HEAD
=======
    public String getReductionIntervalStore() {
        return reductionIntervalStore;
    }

    public void setReductionIntervalStore(String reductionIntervalStore) {
        this.reductionIntervalStore = reductionIntervalStore;
    }

    public int getReductionAmount() {
        return reductionAmount;
    }

    public void setReductionAmount(int reductionAmount) {
        this.reductionAmount = reductionAmount;
    }

    public int getPriceFloor() {
        return priceFloor;
    }

    public void setPriceFloor(int priceFloor) {
        this.priceFloor = priceFloor;
    }

    public String getReductionInterval() {
        return reductionInterval;
    }

    public void setReductionInterval(String reductionInterval) {
        this.reductionInterval = reductionInterval;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

>>>>>>> 5038a01 (added the shipping date)
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


<<<<<<< HEAD

=======
>>>>>>> 5038a01 (added the shipping date)
