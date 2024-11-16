package com.example.BidlySignUpService.repo;

import com.example.BidlySignUpService.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
public interface UserInfoRepo extends JpaRepository<UserInfo, Long>{
}
