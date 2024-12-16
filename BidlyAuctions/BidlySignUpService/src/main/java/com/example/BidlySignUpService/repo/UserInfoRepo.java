package com.example.BidlySignUpService.repo;

import com.example.BidlySignUpService.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
//Interface for DB operations. Extending JpaRepo allows for the use of general DB operations without the need to define them.
public interface UserInfoRepo extends JpaRepository<UserInfo, Long>{

    UserInfo findByUsername(String username);
}
