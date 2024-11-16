package com.example.BidlyPagesService.controller;


import com.example.BidlyPagesService.api.ApiService;
import com.example.BidlyPagesService.dto.Auction;
import com.example.BidlyPagesService.dto.CatalogueItem;
import com.example.BidlyPagesService.webSocket.AuctionWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
public class AdminController {

    @Autowired
    private ApiService apiService;

    @Autowired
    AuctionWebSocketHandler webSocketHandler;

    @GetMapping("/submit-auction")
    public String getAuction(Model model){
        return "AdminAddAuctions";
    }

    @PostMapping("/submit-auction")
    public String addAuction(@RequestParam String title,
                             @RequestParam String desc,
                             @RequestParam int startingPrice,
                             @RequestParam String type,
                             @RequestParam int days,
                             @RequestParam int hours,
                             @RequestParam int mins) throws IOException {

        String timeRemaining = String.format("%dD:%dh:%dm:%ds", days, hours, mins, 0);;
        Auction auction = new Auction(title, desc, startingPrice, type, timeRemaining);

        CatalogueItem newCatalogue = apiService.callCatalogueAddAuction(auction);

        if(newCatalogue != null){
            webSocketHandler.addCatalogueItem(newCatalogue);
            return "redirect:/catalogue";
        }else{
            return "submit-auction";
        }
    }
}
