package com.example.BidlyCatalogue.service;
import com.example.BidlyCatalogue.dto.Auction;
import com.example.BidlyCatalogue.dto.CatalogueItem;
import com.example.BidlyCatalogue.dto.UpdateAuctionRequest;
import com.example.BidlyCatalogue.repo.AuctionRepo;
import com.example.BidlyCatalogue.repo.CatalogueRepo;
import com.example.BidlyCatalogue.repo.PaymentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;


@Service
public class CatalogueService {

    @Autowired
    private AuctionRepo auctionRepo;

    @Autowired
    private CatalogueRepo catalogueRepo;

    @Autowired
    private PaymentRepo paymentRepo;

    @Transactional
    public Auction addAuction(Auction auction){
        try{
            auction = auctionRepo.save(auction);
            System.out.println("Auction Add Passed");
            updateCatalogue(auction);
        }catch (DataIntegrityViolationException e){
            System.out.println("Auction Add Failed");
            return null;
        }
        return auction;
    }

    //Adds a new catalogue item into the Auctions DB
    @Transactional
    public CatalogueItem updateCatalogue(Auction auction){
        CatalogueItem catalogueItem = new CatalogueItem();
        catalogueItem.setAid(auction.getAid());
        catalogueItem.setTitle(auction.getTitle());
        catalogueItem.setHighestBid(auction.getHighestBid());
        catalogueItem.setAuctionTime(auction.getTimeRemaining());

        try{
            catalogueItem = catalogueRepo.save(catalogueItem);
            System.out.println("catalogue Add Passed");
            return catalogueItem;
        } catch (Exception e) {
            System.out.println("Cataloged Add Failed");
            return null;
        }
    }

    @Transactional
    public List<CatalogueItem> fetchCatalogue (){
        return catalogueRepo.findAll();
    }


    @Transactional
    public Auction fetchAuction(Long aid){

        return auctionRepo.findByAid(aid);
    }

    public boolean removeAuction(Long aid){
        Optional<Auction> auction = auctionRepo.findById(aid);
        Auction realAuction = auction.get();
        if(auction!= null){
            auctionRepo.delete(realAuction);
            return true;
        }
        return false;
    }

    @Transactional
    public CatalogueItem fetchCatalogueItem(Long aid){
        return catalogueRepo.findByAid(aid);
    }

    public boolean removeCatalogue(Long id){
        Optional<CatalogueItem> catalogue = catalogueRepo.findById(id);
        CatalogueItem realCatalogue = catalogue.get();
        if(catalogue!=null){
            catalogueRepo.delete(realCatalogue);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean updateBid(UpdateAuctionRequest updateRequest){
        //Update the bid for the current auction in the DB
        Auction auction = auctionRepo.findByAid(updateRequest.getAid());
        auction.setHighestBid(updateRequest.getBid());
        auction.setUserid(updateRequest.getUid());
        auctionRepo.save(auction);

        //Update the bid for the current catalogue item in the DB
        CatalogueItem catalogueItem = catalogueRepo.findByAid(updateRequest.getAid());
        catalogueItem.setHighestBid(updateRequest.getBid());
        catalogueRepo.save(catalogueItem);
        return true;
    }
}
