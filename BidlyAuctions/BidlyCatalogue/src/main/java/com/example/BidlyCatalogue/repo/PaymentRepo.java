package com.example.BidlyCatalogue.repo;

import com.example.BidlyCatalogue.dto.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepo extends JpaRepository<Payment,Long>{
    Payment findByAid(Long id);
}
