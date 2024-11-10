package com.example.BidlyCatalogue.repo;

import com.example.BidlyCatalogue.dto.Auction;
import com.example.BidlyCatalogue.dto.CatalogueItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CatalogueRepo extends JpaRepository<CatalogueItem,Long> {
}
