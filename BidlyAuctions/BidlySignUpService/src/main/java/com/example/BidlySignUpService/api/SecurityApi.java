package com.example.BidlySignUpService.api;


import com.example.BidlySignUpService.dto.UserDataDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;

@Service
public class SecurityApi {

    private final RestTemplate restTemplate;


    public SecurityApi (RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    public boolean callSecurityService(UserDataDTO data){
        String url = "http://localhost:8083/api/sec/sqli-check";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<UserDataDTO> requestEntity = new HttpEntity<>(data,headers);

        ResponseEntity<Boolean> reponse = restTemplate.postForEntity(url, requestEntity, Boolean.class);

        return reponse.getBody();
    }
}
