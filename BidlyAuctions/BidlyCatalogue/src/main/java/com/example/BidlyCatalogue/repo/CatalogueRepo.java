package com.example.BidlyCatalogue.repo;

import com.example.BidlyCatalogue.dto.Auction;
import com.example.BidlyCatalogue.dto.CatalogueItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//Interface for the Auctions database
public interface CatalogueRepo extends JpaRepository<CatalogueItem,Long> {
    CatalogueItem findByAid(Long id);

}
