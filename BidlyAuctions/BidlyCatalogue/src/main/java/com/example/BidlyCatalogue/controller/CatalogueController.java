package com.example.BidlyCatalogue.controller;


import com.example.BidlyCatalogue.api.LiveServerApi;
import com.example.BidlyCatalogue.dto.Auction;
import com.example.BidlyCatalogue.dto.CatalogueItem;
import com.example.BidlyCatalogue.dto.PaymentInfo;
import com.example.BidlyCatalogue.dto.UpdateAuctionRequest;
import com.example.BidlyCatalogue.service.CatalogueService;
import com.example.BidlyCatalogue.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

//REST Controller for performing operations from Pages Microservice.
@RestController
@RequestMapping("/api/catalogue")
public class CatalogueController {

    @Autowired
    private CatalogueService catalogueService;

    @Autowired
    private LiveServerApi liveServerApi;

    @Autowired
    private PaymentService paymentService;

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
        System.out.println("Rest Fetch"+auction.getAid());

        return ResponseEntity.ok(auction);
    }

    @PostMapping("/fetch-catalogue")
    public List<CatalogueItem> getCatalogue(){
        return catalogueService.fetchCatalogue();
    }

    @PostMapping("/place-bid")
    public ResponseEntity<Boolean> placeBid(@RequestBody UpdateAuctionRequest updateRequest) {
        System.out.println(updateRequest.getUid());
        boolean success = catalogueService.updateBid(updateRequest);
        if(success){
            liveServerApi.callLiveServerUpdateBid(updateRequest);
            return ResponseEntity.ok(true);
        }else{
            return ResponseEntity.ok(false);
        }
    }

    @PostMapping("/fetch-catalogueitem")
    public ResponseEntity<CatalogueItem> fetchCatalogueItem(@RequestBody long itemid){
        return ResponseEntity.ok(catalogueService.fetchCatalogueItem(itemid));
    }

    @PostMapping("/process")
    public ResponseEntity<Boolean> doPaymentProcess(@RequestBody PaymentInfo paymentInfo){
        boolean result = paymentService.processPayment(paymentInfo);
        if(result == true){
            return ResponseEntity.ok(true);
        }
        else{
            return ResponseEntity.ok(false);
        }
    }

    @PostMapping("/remove-auction")
    public ResponseEntity<Boolean>doRemoveAuction(@RequestBody long aid){
        catalogueService.removeAuction(aid);
        return ResponseEntity.ok(true);
    }
<<<<<<< HEAD
=======

    @PostMapping("/buyout")
    public ResponseEntity<Boolean> processBuyout(@RequestBody UpdateAuctionRequest updateRequest) {
        boolean success = catalogueService.setBuyoutWinner(updateRequest);
        liveServerApi.callLiveServerBuyout(updateRequest);
        return  ResponseEntity.ok(success);
    }

>>>>>>> 5038a01 (added the shipping date)
}
