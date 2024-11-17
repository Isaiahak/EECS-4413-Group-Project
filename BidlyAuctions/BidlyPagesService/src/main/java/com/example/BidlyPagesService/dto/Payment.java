package com.example.BidlyPagesService.dto;




public class Payment {

    private int paymentID;

    private int itemID;

    private int auctionID;

    private long finalPrice;

    public long getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(int paymentID) {
        this.paymentID = paymentID;
    }

    public long getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public long getAuctionID() {
        return auctionID;
    }

    public void setAuctionID(int auctionID) {
        this.auctionID = auctionID;
    }

    public long getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(long finalPrice) {
        this.finalPrice = finalPrice;
    }

}
