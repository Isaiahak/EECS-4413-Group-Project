package com.example.BidlyCatalogue.service;


import com.example.BidlyCatalogue.dto.Auction;
import com.example.BidlyCatalogue.dto.CatalogueItem;
import com.example.BidlyCatalogue.repo.AuctionRepo;
import com.example.BidlyCatalogue.repo.CatalogueRepo;
import com.example.BidlyCatalogue.websocket.AuctionWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CatalogueService {

    @Autowired
    private AuctionRepo auctionRepo;

    @Autowired
    private CatalogueRepo catalogueRepo;

    @Autowired
    private AuctionWebSocketHandler webSocketHandler;

    @Transactional
    public boolean addAuction(Auction auction){
        try{
            auction = auctionRepo.save(auction);
            System.out.println("Auction Add Passed");
            updateAuction(auction);
        }catch (DataIntegrityViolationException e){
            System.out.println("Auction Add Failed");
            return false;
        }
        return true;
    }

    @Transactional
    public void updateAuction(Auction auction){
        CatalogueItem catalogueItem = new CatalogueItem();

        catalogueItem.setAid(auction.getAid());
        catalogueItem.setTitle(auction.getTitle());
        catalogueItem.getCurrent_highest_bid(auction.getHighestBid());
        catalogueItem.setTime_remaining(auction.getTimeRemaining());

        try{
            catalogueRepo.save(catalogueItem);
            System.out.println("catalogue Add Passed");
        } catch (Exception e) {
            System.out.println("Cataloged Add Failed");
        }
    }
}
