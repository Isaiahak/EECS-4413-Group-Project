package com.example.BidlySignUpService.service;
import com.example.BidlySignUpService.api.SecurityApi;
import com.example.BidlySignUpService.dto.UserDataDTO;
import com.example.BidlySignUpService.model.UserCreds;
import com.example.BidlySignUpService.model.UserInfo;
import com.example.BidlySignUpService.repo.UserCredsRepo;
import com.example.BidlySignUpService.repo.UserInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.ArrayList;

//Sign Up Service
//TODO: Move password encoding to BidlySecurity.
//TODO: Convert all params from signUp method into a DTO | this service will still work with the current implementation
@Service
public class SignUpService {

    @Autowired
    private UserCredsRepo ucRepo;

    @Autowired
    private UserInfoRepo uinfRepo;

    @Autowired
    private SecurityApi securityApi;

    private final BCryptPasswordEncoder passEncoder = new BCryptPasswordEncoder();


    @Transactional
    public boolean signUp(String username, String password, String firstName, String lastName,
                          String street, String city, String province, String zipcode) {
        // Check if username already exists

        UserDataDTO data = new UserDataDTO();

        data.add(username);
        data.add(password);
        data.add(firstName);
        data.add(lastName);
        data.add(street);
        data.add(city);
        data.add(province);
        data.add(zipcode);

        //perform SQLi check before performing DB operation.
        if(securityApi.callSecurityService(data)){
            return false;
        }

        System.out.println("Checking for Existing User");
        if (ucRepo.existsByUsername(username)) {
            return false;
        }

        //This is to be moved to BidlySecurity
        String passHash = passEncoder.encode(password);
        System.out.println("Hashed Pass is " + passHash);


        UserCreds uc = new UserCreds();
        uc.setUsername(username);
        uc.setPassword(passHash);
        try {
            uc = ucRepo.save(uc); // Try to save UserCreds
            System.out.println("Creds success");
        } catch (DataIntegrityViolationException e) {
            System.out.println("Creds fail");
            return false; // Indicate failure

        }

        UserInfo uinfo = new UserInfo();
        uinfo.setUsername(uc.getUsername());
        uinfo.setFirstName(firstName);
        uinfo.setLastName(lastName);
        uinfo.setStreet(street);
        uinfo.setCity(city);
        uinfo.setProvince(province);
        uinfo.setZipcode(zipcode);
        try {
            uinfRepo.save(uinfo); // Save UserInfo
            System.out.println("info success");
        } catch (Exception e) {
            System.out.println("info fail");
            return false;
        }
        return true;
    }
}
