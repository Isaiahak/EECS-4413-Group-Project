package com.example.BidlySignUpService.service;


import com.example.BidlySignUpService.api.SecurityApi;
import com.example.BidlySignUpService.dto.LoginRequestDTO;
import com.example.BidlySignUpService.dto.UserDataDTO;
import com.example.BidlySignUpService.model.UserCreds;
import com.example.BidlySignUpService.model.UserInfo;
import com.example.BidlySignUpService.repo.UserCredsRepo;
import com.example.BidlySignUpService.repo.UserInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

//Login Service
//TODO: Move password encoding to the security component.
@Service
public class LoginService {

    @Autowired
    private UserCredsRepo ucRepo;

    @Autowired
    private UserInfoRepo userInfoRepo;

    @Autowired
    private SecurityApi securityApi;

    //To be moved to BidlySecurity
    private final BCryptPasswordEncoder passEncoder = new BCryptPasswordEncoder();

    //Process login request.
    public String login(LoginRequestDTO lr) {
        String username = lr.getUsername();
        String password = lr.getPassword();

        //Ensure Security check passes before performing a DB operation
        if(loginSecurityCheck(username, password)){
            System.out.println("security fail");
            return null;
        }
        System.out.println("security pass");
        return matchData(username, password);

    }

    //REST Call to Security Microservice for SQLI Chcek.
    public boolean loginSecurityCheck(String username, String password){
        UserDataDTO data = new UserDataDTO();
        data.add(username);
        data.add(password);
        return securityApi.callSecurityService(data);
    }

    //Perform a DB match to confirm login credentials.
    public String matchData(String username, String pass){
        UserCreds uc = ucRepo.findByUsername(username);
        if(passEncoder.matches(pass, uc.getPassword())){
            return uc.getUsername();
        }
        else{
            return null;
        }
    }

    public Long fetchUid(String username){
        return ucRepo.findByUsername(username).getUid();
    }

    public UserInfo fetchUserInfo(long username){
        UserInfo info = userInfoRepo.findByUsername(username);
        return info;
    }
}
