package com.example.BidlyPagesService.controller;


import com.example.BidlyPagesService.api.ApiService;
import com.example.BidlyPagesService.dto.Auction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {

    @Autowired
    private ApiService apiService;

    @GetMapping("/submit-auction")
    public String getAuction(Model model){
        return "AdminAddAuctions";
    }

    @PostMapping("/submit-auction")
    public String addAuction(@RequestParam String title,
                             @RequestParam String desc,
                             @RequestParam int startingPrice,
                             @RequestParam String type,
                             @RequestParam String timeRemaining){
        Auction auction = new Auction(title, desc, startingPrice, type, timeRemaining);

        boolean success = apiService.callCatalogueAddAuction(auction);

        if(success){
            return "redirect:/catalogue";
        }else{
            return "submit-auction";
        }
    }
}
