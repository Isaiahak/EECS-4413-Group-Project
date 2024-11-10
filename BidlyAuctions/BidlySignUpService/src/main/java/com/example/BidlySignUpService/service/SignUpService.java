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

        if(securityApi.callSecurityService(data)){
            System.out.println("Weaint letting that happen");
            return false;
        }


        System.out.println("Checking for Existing User");
        if (ucRepo.existsByUsername(username)) {
            System.out.println("Exists");
            return false; // Indicate that the username is already in use
        }

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

        UserInfo uinf = new UserInfo();
        uinf.setUid(uc.getUid());
        uinf.setFirstName(firstName);
        uinf.setLastName(lastName);
        uinf.setStreet(street);
        uinf.setCity(city);
        uinf.setProvince(province);
        uinf.setZipcode(zipcode);

        try {
            uinfRepo.save(uinf); // Save UserInfo
            System.out.println("info success");
        } catch (Exception e) {
            // Handle any exceptions that may occur during the save
            System.out.println("info fail");
            return false; // Indicate failure
        }

        return true; // Indicate success
    }
}
