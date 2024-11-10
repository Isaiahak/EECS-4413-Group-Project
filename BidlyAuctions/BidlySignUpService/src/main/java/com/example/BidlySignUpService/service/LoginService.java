package com.example.BidlySignUpService.service;


import com.example.BidlySignUpService.api.SecurityApi;
import com.example.BidlySignUpService.dto.LoginRequestDTO;
import com.example.BidlySignUpService.dto.UserDataDTO;
import com.example.BidlySignUpService.model.UserCreds;
import com.example.BidlySignUpService.repo.UserCredsRepo;
import org.apache.logging.log4j.message.AsynchronouslyFormattable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
public class LoginService {

    @Autowired
    private UserCredsRepo ucRepo;

    @Autowired
    private SecurityApi securityApi;

    private final BCryptPasswordEncoder passEncoder = new BCryptPasswordEncoder();

    public boolean login(LoginRequestDTO lr) {
        String username = lr.getUsername();
        String password = lr.getPassword();

        if(!loginSecurityCheck(username, password)){
            return false;
        }

        return matchData(username, password);

    }

    public boolean loginSecurityCheck(String username, String password){
        UserDataDTO data = new UserDataDTO();
        data.add(username);
        data.add(password);

        return securityApi.callSecurityService(data);
    }

    public boolean matchData(String username, String pass){
        UserCreds uc = ucRepo.findByUsername(username);

        return passEncoder.matches(pass, uc.getPassword());
    }
}
