package com.example.BidlyLiveServer.repo;

import com.example.BidlyLiveServer.dto.CatalogueItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CatalogueDB extends JpaRepository<CatalogueItem, Long> {
}
