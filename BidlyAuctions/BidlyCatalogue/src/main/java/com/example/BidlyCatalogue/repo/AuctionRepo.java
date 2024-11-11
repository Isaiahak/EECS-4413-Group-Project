package com.example.BidlyCatalogue.repo;

import com.example.BidlyCatalogue.dto.Auction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

@Repository
public interface AuctionRepo extends JpaRepository<Auction,Long> {
    Auction findByAid(Long id);
}
