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

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

@Service
public class LiveServerService {


    private ArrayList<LiveUpdate> updates = new ArrayList<>();


    private ArrayList<LiveUpdate> dutchUpdates = new ArrayList<>();

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
            if(item.getType().equals("forward")){
                LiveUpdate timeUpdate = new LiveUpdate();
                timeUpdate.setTitle(item.getTitle());
                timeUpdate.setAid(item.getAid());
                timeUpdate.setTimeRemaining(item.getAuctionTime());
                timeUpdate.setHighestBid(item.getHighestBid());
                updates.add(timeUpdate);
                System.out.println(item.getTitle());
            } else if(item.getType().equals("dutch")){
                LiveUpdate dutchUpdate = new LiveUpdate();
                dutchUpdate.setAid(item.getAid());
                dutchUpdate.setTitle(item.getTitle());
                dutchUpdate.setHighestBid(item.getHighestBid());
                dutchUpdate.setTimeRemaining("NOW");
                dutchUpdate.setReductionIntervalStore(item.getReductionInterval());
                dutchUpdate.setReductionAmount(item.getReductionAmount());
                dutchUpdate.setReductionInterval(item.getReductionInterval());
                dutchUpdate.setPriceFloor(dutchUpdate.getHighestBid()/10);
                dutchUpdates.add(dutchUpdate);
                System.out.println(item.getTitle());
            }

        }
        for(LiveUpdate u : updates){
            System.out.println(u.getTitle());
        }
    }

    public void updateTime() throws Exception {
        if(updates.size() == 0){
            return ;
        }
        for(LiveUpdate time: updates){

            if(time.getTimeRemaining().equals("CLOSED")){
                auctionEnd(time.getAid());
                continue;
            }

            StringTokenizer tokenizer = new StringTokenizer(time.getTimeRemaining(), ":Dhms", false);
            int days = Integer.parseInt(tokenizer.nextToken().trim());
            int hours = Integer.parseInt(tokenizer.nextToken().trim());
            int minutes = Integer.parseInt(tokenizer.nextToken().trim());
            int seconds = Integer.parseInt(tokenizer.nextToken().trim());

            seconds -= interval;

            if (days == 0 && hours == 0 && minutes == 0 && seconds == 0) {
                time.setTimeRemaining("CLOSED");
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

    public void updateDutch() throws Exception {
        if(dutchUpdates.size() == 0){
            return ;
        }
        for(LiveUpdate dutchUpdate: dutchUpdates){

            StringTokenizer tokenizer = new StringTokenizer(dutchUpdate.getReductionInterval(), ":Dhms", false);
            int days = Integer.parseInt(tokenizer.nextToken().trim());
            int hours = Integer.parseInt(tokenizer.nextToken().trim());
            int minutes = Integer.parseInt(tokenizer.nextToken().trim());
            int seconds = Integer.parseInt(tokenizer.nextToken().trim());

            seconds -= interval;

            if (days == 0 && hours == 0 && minutes == 0 && seconds == 0) {
                if(dutchUpdate.getHighestBid() <= dutchUpdate.getPriceFloor()){
                    return;
                }else{
                    int newPrice = dutchUpdate.getHighestBid()-dutchUpdate.getReductionAmount();
                    if(dutchUpdate.getPriceFloor() >= newPrice){
                        dutchUpdate.setHighestBid(dutchUpdate.getPriceFloor());
                    }
                    dutchUpdate.setHighestBid(newPrice);
                    dutchUpdate.setReductionInterval(dutchUpdate.getReductionIntervalStore());
                }
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
            dutchUpdate.setReductionInterval(String.format("%dD:%dh:%dm:%ds", days, hours, minutes, seconds));
        }
    }

    @Scheduled(fixedDelay = 1000)
    public void pushUpdates() throws Exception {
        updateTime();
        updateDutch();
        liveServerWebSocketHandler.sendAuctionUpdate(updates);
        liveServerWebSocketHandler.sendAuctionUpdate(dutchUpdates);
        for(LiveUpdate update: updates){
            System.out.println(update.getTitle()+update.getTimeRemaining());
        }
        for(LiveUpdate update: dutchUpdates){
            System.out.println(update.getTitle()+update.getTimeRemaining());
        }
    }

    public void addAuction(CatalogueItem newAuction){
        if(newAuction.getType().equals("forward")){
            LiveUpdate timeUpdate = new LiveUpdate();
            timeUpdate.setType("forward");
            timeUpdate.setTitle(newAuction.getTitle());
            timeUpdate.setAid(newAuction.getAid());
            timeUpdate.setTimeRemaining(newAuction.getAuctionTime());
            timeUpdate.setHighestBid(newAuction.getHighestBid());
            updates.add(timeUpdate);
            System.out.println(newAuction.getTitle());
        } else if(newAuction.getType().equals("dutch")){
            LiveUpdate dutchUpdate = new LiveUpdate();
            dutchUpdate.setType("dutch");
            dutchUpdate.setAid(newAuction.getAid());
            dutchUpdate.setTitle(newAuction.getTitle());
            dutchUpdate.setHighestBid(newAuction.getHighestBid());
            dutchUpdate.setTimeRemaining("NOW");
            dutchUpdate.setReductionIntervalStore(newAuction.getReductionInterval());
            dutchUpdate.setReductionAmount(newAuction.getReductionAmount());
            dutchUpdate.setReductionInterval(newAuction.getReductionInterval());
            dutchUpdate.setPriceFloor(dutchUpdate.getHighestBid()/10);
            dutchUpdates.add(dutchUpdate);
            System.out.println(newAuction.getTitle());
        }
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
    public void auctionBuyout(Long aid){
        for(LiveUpdate update : dutchUpdates){
            if(update.getAid().equals(aid)){
                update.setTimeRemaining("CLOSED");
            }
        }
    }
}
