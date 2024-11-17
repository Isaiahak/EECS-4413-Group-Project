package com.example.BidlyCatalogue.repo;

import com.example.BidlyCatalogue.dto.Auction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

//Interface for the Auctions database.
@Repository
public interface AuctionRepo extends JpaRepository<Auction,Long> {
    Auction findByAid(Long id);
}
