package com.example.BidlyCatalogue.dto;

import jakarta.persistence.*;

@Entity
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "paymentID")
    private Long paymentID;

    @Column(name = "auctionId")
    private long itemID;

    @Column(name = "aid")
    private long aid;

    @Column(name = "finalPrice")
    private int finalPrice;

    public long getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(Long paymentID) {
        this.paymentID = paymentID;
    }

    public long getItemID() {
        return itemID;
    }

    public void setItemID(long itemID) {
        this.itemID = itemID;
    }

    public long getAuctionID() {
        return aid;
    }

    public void setAuctionID(long aid) {
        this.aid = aid;
    }

    public long getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(int finalPrice) {
        this.finalPrice = finalPrice;
    }

}
