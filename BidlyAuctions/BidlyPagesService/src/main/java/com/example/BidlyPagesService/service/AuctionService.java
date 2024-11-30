package com.example.BidlyPagesService.service;


import com.example.BidlyPagesService.dto.Auction;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class AuctionService {

    private final List<Auction> auctionList = new CopyOnWriteArrayList<>();

    public void updateAuctions(List<Auction> auctions){
        auctionList.clear();
        auctionList.addAll(auctions);

    }
    /*
    public boolean removeAuction(Long aid){
        Auction auction = auctionRepo.findById(aid);
        if(auction!= null){
            auctionRepo.delete(auction);
            return true;
        }
        return false;
    }
    */
    public List<Auction> getAllAuctions(){
        return auctionList;
    }
}
