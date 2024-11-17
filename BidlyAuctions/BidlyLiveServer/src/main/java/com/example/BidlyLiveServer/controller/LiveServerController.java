package com.example.BidlyLiveServer.controller;


import com.example.BidlyLiveServer.dto.Auction;
import com.example.BidlyLiveServer.dto.CatalogueItem;
import com.example.BidlyLiveServer.dto.UpdateAuctionRequest;
import com.example.BidlyLiveServer.service.LiveServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/live")
public class LiveServerController {
    @Autowired
    private LiveServerService liveServerService;

    @PostMapping("/new-auction")
    public void updateTime(@RequestBody CatalogueItem newCatalogueItem) {
        liveServerService.addAuction(newCatalogueItem);
    }

    @PostMapping("/update-bid")
    public void updateBid(@RequestBody UpdateAuctionRequest updateRequest){
        liveServerService.updateAuctionBid(updateRequest);
    }

    @PostMapping("/remove-auction")
    public void removeAuction(@RequestBody Auction auction){
        liveServerService.removeAuction(auction);
    }

}
