package com.example.BidlyCatalogue.controller;


import com.example.BidlyCatalogue.api.LiveServerApi;
import com.example.BidlyCatalogue.dto.Auction;
import com.example.BidlyCatalogue.dto.CatalogueItem;
import com.example.BidlyCatalogue.dto.UpdateAuctionRequest;
import com.example.BidlyCatalogue.service.CatalogueService;
import com.example.BidlyCatalogue.websocket.AuctionWebSocketHandler;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    private LiveServerApi liveServerApi;

    @Autowired
    private AuctionWebSocketHandler auctionWebSocketHandler;

    @PostMapping("/add-auction")
    public CatalogueItem addAuction(@RequestBody Auction auction) throws Exception {
        Auction createdAuction = catalogueService.addAuction(auction);
        if(createdAuction == null){
            return null;
        }
        CatalogueItem newCatalogueItem = catalogueService.updateCatalogue(createdAuction);
        liveServerApi.callLiveServerAddAuction(newCatalogueItem);
        return newCatalogueItem;
    }

    @PostMapping("/fetch-auction")
    public ResponseEntity<Auction> fetchAuction(@RequestBody Long aid){
        Auction auction = catalogueService.fetchAuction(aid);

        return ResponseEntity.ok(auction);
    }

    @PostMapping("/fetch-catalogue")
    public List<CatalogueItem> getCatalogue(){
        return catalogueService.fetchCatalogue();
    }

    @PostMapping("/place-bid")
    public ResponseEntity<Boolean> placeBid(@RequestBody UpdateAuctionRequest updateRequest) {
        System.out.println(updateRequest.getAid());
        boolean success = catalogueService.updateBid(updateRequest);
        if(success){
            liveServerApi.callLiveServerUpdateBid(updateRequest);
            return ResponseEntity.ok(true);
        }else{
            return ResponseEntity.ok(false);
        }
    }
}
