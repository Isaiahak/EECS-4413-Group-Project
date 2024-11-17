package com.example.BidlyCatalogue.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "catalogue")
public class Payment {
    @Id
    @Column(name = "paymentID")
    private long paymentID;

    @Column(name = "itemID")
    private long itemID;

    @Column(name = "auctionID")
    private long auctionID;

    @Column(name = "finalPrice")
    private int finalPrice;

    public long getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(int paymentID) {
        this.paymentID = paymentID;
    }

    public long getItemID() {
        return itemID;
    }

    public void setItemID(long itemID) {
        this.itemID = itemID;
    }

    public long getAuctionID() {
        return auctionID;
    }

    public void setAuctionID(long auctionID) {
        this.auctionID = auctionID;
    }

    public long getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(int finalPrice) {
        this.finalPrice = finalPrice;
    }

}
