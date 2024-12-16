package com.example.BidlyPagesService.controller;


import com.example.BidlyPagesService.api.ApiService;
import com.example.BidlyPagesService.dto.Auction;
import com.example.BidlyPagesService.dto.CatalogueItem;
import com.example.BidlyPagesService.dto.DutchAuction;
import com.example.BidlyPagesService.webSocket.AuctionWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

//Controller for admin/testing purposes, will not be available to the users
@Controller
public class AdminController {

    @Autowired
    private ApiService apiService;

    @Autowired
    AuctionWebSocketHandler webSocketHandler;

    //Get Mapping for submit Auction page. Here, we will be able to add new auctions
    @GetMapping("/submit-auction")
    public String getAuction(Model model){
        return "AdminSelectCreateAuction";
    }

    //Post Mapping for submit auction page, to add the new auction in to the system.
//    @PostMapping("/add-auction")
//    public String addAuction(@RequestParam String title,
//                             @RequestParam String desc,
//                             @RequestParam int startingPrice,
//                             @RequestParam String type,
//                             @RequestParam int days,
//                             @RequestParam int hours,
//                             @RequestParam int mins) throws IOException {
//
//        //Create Auction DTO to send to cataloge via REST
//        //Catalogue will complete the creation process
//        String timeRemaining = String.format("%dD:%dh:%dm:%ds", days, hours, mins, 0);
//
//        Auction auction = new Auction(title, desc, startingPrice, type, timeRemaining);
//
//        //Catalogue Microservice returns the newly created auction catalogue item
//        //This service will use this catalogue Object to push a dynamic update to clients
//        CatalogueItem newCatalogue = apiService.callCatalogueAddAuction(auction);
//
//        if(newCatalogue != null){
//            webSocketHandler.addCatalogueItem(newCatalogue);
//            return "redirect:/catalogue";
//        }else{
//            return "submit-auction";
//        }
//    }

    @GetMapping("/add-forward")
    public String addForwardAuction(Model model){
        return "AdminAddForwardAuction";
    }

    @PostMapping("/add-forward")
    public String submitForwardAuction(@RequestParam String title,
                                       @RequestParam String desc,
                                       @RequestParam int startingPrice,
                                       @RequestParam String type,
                                       @RequestParam int days,
                                       @RequestParam int hours,
                                       @RequestParam int mins) throws IOException {
        String timeRemaining = String.format("%dD:%dh:%dm:%ds", days, hours, mins, 0);

        Auction auction = new Auction();
        auction.setTitle(title);
        auction.setDesc(desc);
        auction.setHighestBid(startingPrice);
        auction.setType(type);
        auction.setTimeRemaining(timeRemaining);

        //Catalogue Microservice returns the newly created auction catalogue item
        //This service will use this catalogue Object to push a dynamic update to clients
        CatalogueItem newCatalogue = apiService.callCatalogueAddAuction(auction);

        if(newCatalogue != null){
            webSocketHandler.addCatalogueItem(newCatalogue);
            return "redirect:/catalogue";
        }else{
            return "submit-auction";
        }
    }

    @PostMapping("/add-dutch")
    public String submitDutchAuction(@RequestParam String title,
                                       @RequestParam String desc,
                                       @RequestParam int startingPrice,
                                       @RequestParam String type,
                                       @RequestParam int days,
                                       @RequestParam int hours,
                                       @RequestParam int mins,
                                     @RequestParam int reductionAmount) throws IOException {
        String timeRemaining = String.format("%dD:%dh:%dm:%ds", days, hours, mins, 0);

        Auction dutchAuction = new Auction();
        dutchAuction.setTitle(title);
        dutchAuction.setDesc(desc);
        dutchAuction.setHighestBid(startingPrice);
        dutchAuction.setType(type);
        dutchAuction.setTimeRemaining(timeRemaining);
        dutchAuction.setReductionInterval(timeRemaining);
        dutchAuction.setReductionAmount(reductionAmount);
        //Catalogue Microservice returns the newly created auction catalogue item
        //This service will use this catalogue Object to push a dynamic update to clients
        CatalogueItem newCatalogue = apiService.callCatalogueAddAuction(dutchAuction);

        if(newCatalogue != null){
            webSocketHandler.addCatalogueItem(newCatalogue);
            return "redirect:/catalogue";
        }else{
            return "submit-auction";
        }
    }

    @GetMapping("/add-dutch")
    public String addDutchAuction(Model model){
        return "AdminAddDutchAuction";
    }
}
