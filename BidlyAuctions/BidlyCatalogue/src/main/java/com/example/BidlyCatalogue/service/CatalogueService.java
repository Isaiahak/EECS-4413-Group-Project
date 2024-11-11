package com.example.BidlyCatalogue.service;


import com.example.BidlyCatalogue.dto.Auction;
import com.example.BidlyCatalogue.dto.CatalogueItem;
import com.example.BidlyCatalogue.repo.AuctionRepo;
import com.example.BidlyCatalogue.repo.CatalogueRepo;
import com.example.BidlyCatalogue.websocket.AuctionWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
            updateCatalogue(auction);
        }catch (DataIntegrityViolationException e){
            System.out.println("Auction Add Failed");
            return false;
        }
        return true;
    }

    @Transactional
    public void updateCatalogue(Auction auction){
        CatalogueItem catalogueItem = new CatalogueItem();

        catalogueItem.setAid(auction.getAid());
        catalogueItem.setTitle(auction.getTitle());
        catalogueItem.setHighestBid(auction.getHighestBid());
        catalogueItem.setAuctionTime(auction.getTimeRemaining());

        try{
            catalogueRepo.save(catalogueItem);
            System.out.println("catalogue Add Passed");
        } catch (Exception e) {
            System.out.println("Cataloged Add Failed");
        }
    }

    @Transactional
    public List<CatalogueItem> fetchCatalogue (){
        return catalogueRepo.findAll();
    }

    @Scheduled(fixedDelay = 5000)
    public void sendCatalogueUpdate() throws Exception {
        List<CatalogueItem> catalogueList = fetchCatalogue();
        for(CatalogueItem item : catalogueList){
            System.out.println(item.getAuctionTime()+item.getHighestBid());
        }
        webSocketHandler.sendAuctionUpdate(catalogueList);
    }

    @Transactional
    public Auction fetchAuction(Long aid){

        return auctionRepo.findByAid(aid);
    }
}
