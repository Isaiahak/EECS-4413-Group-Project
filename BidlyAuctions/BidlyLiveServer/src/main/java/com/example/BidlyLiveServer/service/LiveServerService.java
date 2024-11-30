package com.example.BidlyLiveServer.service;



import com.example.BidlyLiveServer.dto.CatalogueItem;
import com.example.BidlyLiveServer.dto.LiveUpdate;
import com.example.BidlyLiveServer.dto.UpdateAuctionRequest;
import com.example.BidlyLiveServer.repo.CatalogueDB;
import com.example.BidlyLiveServer.websocket.LiveServerWebSocketHandler;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

@Service
public class LiveServerService {

    @Autowired
    private ArrayList<LiveUpdate> updates;

    @Autowired
    private LiveServerWebSocketHandler liveServerWebSocketHandler;


    @Autowired
    private CatalogueDB catalogueRepo;

    private final int interval = 1;

    @PostConstruct
    @Transactional
    public void init(){
        List<CatalogueItem> catalogueItems = catalogueRepo.findAll();
        for(CatalogueItem item : catalogueItems){
            LiveUpdate timeUpdate = new LiveUpdate();
            timeUpdate.setTitle(item.getTitle());
            timeUpdate.setAid(item.getAid());
            timeUpdate.setTimeRemaining(item.getAuctionTime());
            timeUpdate.setHighestBid(item.getHighestBid());
            updates.add(timeUpdate);
            System.out.println(item.getTitle());
        }
    }

    public void updateTime() throws Exception {
        if(updates.size() == 0){
            return ;
        }
        for(LiveUpdate time: updates){



            StringTokenizer tokenizer = new StringTokenizer(time.getTimeRemaining(), ":Dhms", false);
            int days = Integer.parseInt(tokenizer.nextToken().trim());
            int hours = Integer.parseInt(tokenizer.nextToken().trim());
            int minutes = Integer.parseInt(tokenizer.nextToken().trim());
            int seconds = Integer.parseInt(tokenizer.nextToken().trim());

            seconds -= interval;

            if (days == 0 && hours == 0 && minutes == 0 && seconds == 0) {
                time.setTimeRemaining("CLOSED");
                auctionEnd(time.getAid());
                continue;  // Stop further updates
            }

            // Handle negative seconds and adjust minutes, hours, and days
            if (seconds < 0) {
                minutes --;  // Convert excess seconds to minutes
                seconds = 60 + seconds % 60; // Adjust seconds back to a positive value
            }

            // Handle negative minutes and adjust hours and days
            if (minutes < 0) {
                hours --;  // Convert excess minutes to hours
                minutes = 60 + minutes % 60; // Adjust minutes back to a positive value
            }

            // Handle negative hours and adjust days
            if (hours < 0) {
                days --;  // Convert excess hours to days
                hours = 24 + hours % 24; // Adjust hours back to a positive value
            }

            // Construct the new time string in the format D: h:m:s
            time.setTimeRemaining(String.format("%dD:%dh:%dm:%ds", days, hours, minutes, seconds));
        }
    }

    @Scheduled(fixedDelay = 1000)
    public void pushUpdates() throws Exception {
        updateTime();
        liveServerWebSocketHandler.sendAuctionUpdate(updates);
        for(LiveUpdate update: updates){
            System.out.println(update.getTitle()+update.getTimeRemaining());
        }
    }

    public void addAuction(CatalogueItem newAuction){
        LiveUpdate newTimeUpdate = new LiveUpdate();
        newTimeUpdate.setAid(newAuction.getAid());
        newTimeUpdate.setTitle(newAuction.getTitle());
        newTimeUpdate.setTimeRemaining(newAuction.getAuctionTime());
        updates.add(newTimeUpdate);
    }

    public void updateAuctionBid(UpdateAuctionRequest updateRequest){
        //inefficient, will switch to hashmap later
        for(LiveUpdate update : updates){
            if(update.getAid().equals(updateRequest.getAid())){
                System.out.println("found");
                update.setHighestBid(updateRequest.getBid());
            }
        }
    }

    public void auctionEnd(Long aid) throws Exception {
        liveServerWebSocketHandler.sendAuctionClosed(aid);
        removeAuction(aid);
    }

    public boolean removeAuction(Long aid){
        boolean returnValue = false;
        for (LiveUpdate update : updates){
            if(update.getAid().equals(aid)){
                updates.remove(update);
                return true;
            }
        }
        return returnValue;
    }
}
