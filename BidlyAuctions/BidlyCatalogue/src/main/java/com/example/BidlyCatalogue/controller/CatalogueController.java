package com.example.BidlyCatalogue.controller;


import com.example.BidlyCatalogue.dto.Auction;
import com.example.BidlyCatalogue.service.CatalogueService;
import com.example.BidlyCatalogue.websocket.AuctionWebSocketHandler;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/catalogue")
public class CatalogueController {

    @Autowired
    private CatalogueService catalogueService;

    @Autowired
    private AuctionWebSocketHandler auctionWebSocketHandler;

    @PostMapping("/add-auction")
    public ResponseEntity<Boolean> addAuction(@RequestBody Auction auction) throws Exception {
        if(catalogueService.addAuction(auction)){
//            List<Auction> auctionList = new ArrayList<>();
//            auctionList.add(auction);
//            auctionList.add(auction);
//            System.out.println("Firing Socket");
//            auctionWebSocketHandler.sendAuctionUpdate(auctionList);
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.ok(false);
    }

    @PostMapping("/fetch-auction")
    public ResponseEntity<Auction> fetchAuction(@RequestBody Long aid){
        Auction auction = catalogueService.fetchAuction(aid);

        return ResponseEntity.ok(auction);
    }
}
