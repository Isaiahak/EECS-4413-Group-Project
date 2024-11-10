package com.example.BidlySignUpService.repo;

import com.example.BidlySignUpService.model.UserCreds;
import com.example.BidlySignUpService.model.UserInfo;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCredsRepo extends JpaRepository<UserCreds,Long> {
    boolean existsByUsername(String username);
    UserCreds findByUsername(String username);
}
