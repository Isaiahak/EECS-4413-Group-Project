package com.example.BidlyPagesService.service;


import com.example.BidlyPagesService.dto.CatalogueItem;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class AuctionService {

    private final List<CatalogueItem> auctionList = new CopyOnWriteArrayList<>();

    public void updateAuctions(List<CatalogueItem> catalogueItems){
        auctionList.clear();
        auctionList.addAll(catalogueItems);

    }

    public List<CatalogueItem> getAllAuctions(){
        return auctionList;
    }
}
