package com.example.BidlyCatalogue.repo;

import com.example.BidlyCatalogue.dto.Auction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuctionRepo extends JpaRepository<Auction,Long> {
}
