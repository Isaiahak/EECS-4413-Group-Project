package com.example.BidlySignUpService.repo;

import com.example.BidlySignUpService.model.UserCreds;
import com.example.BidlySignUpService.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//Interface for DB operations. Extending JpaRepo allows for the use of general DB operations without the need to define them.
//The defined methods are custom DB operations.
@Repository
public interface UserCredsRepo extends JpaRepository<UserCreds,Long> {
    boolean existsByUsername(String username);
    UserCreds findByUsername(String username);

}
