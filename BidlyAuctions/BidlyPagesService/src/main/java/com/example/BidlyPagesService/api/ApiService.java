package com.example.BidlyPagesService.api;
import com.example.BidlyPagesService.dto.Auction;
import com.example.BidlyPagesService.dto.CatalogueItem;
import com.example.BidlyPagesService.dto.LoginRequestDTO;
import com.example.BidlyPagesService.dto.UpdateAuctionRequest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ApiService {

    private final RestTemplate restTemplate;

    public ApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean callSignUpService(String username, String password, String firstName,
                                     String lastName, String street, String city,
                                     String province, String zipcode) {
        String url = "http://localhost:8081/signup"; // Adjust based on your service's URL

        // Create a request body map to send individual parameters
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("username", username);
        requestBody.add("password", password);
        requestBody.add("firstName", firstName);
        requestBody.add("lastName", lastName);
        requestBody.add("street", street);
        requestBody.add("city", city);
        requestBody.add("province", province);
        requestBody.add("zipcode", zipcode);
        return restTemplate.postForObject(url, requestBody, Boolean.class);
    }

    public boolean callLoginService(LoginRequestDTO lr){
        String url = "http://localhost:8081/api/login";

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        System.out.println("seding login request");
        HttpEntity<LoginRequestDTO> requestEntity = new HttpEntity<>(lr, headers);
        ResponseEntity<Boolean> response = restTemplate.postForEntity(url, requestEntity, Boolean.class);

        return response.getBody();
    }

    public CatalogueItem callCatalogueAddAuction(Auction auction){
        String url = "http://localhost:8084/api/catalogue/add-auction";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Auction> requestEntity = new HttpEntity<Auction>(auction, headers);
        ResponseEntity<CatalogueItem> response =
                restTemplate.exchange(url,
                        HttpMethod.POST, requestEntity, new ParameterizedTypeReference<CatalogueItem>() {
                        });
        return response.getBody();
    }

    public Auction callCatalogueGetAuction(Long aid){
        String url = "http://localhost:8084/api/catalogue/fetch-auction";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Long> requestEntity = new HttpEntity<>(aid, headers);
        ResponseEntity<Auction> response = restTemplate.postForEntity(url, requestEntity, Auction.class);

        return response.getBody();
    }

    public List<CatalogueItem> callCatalogueGetCatalogue(){
        String url = "http://localhost:8084/api/catalogue/fetch-catalogue";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<List<CatalogueItem>> response =
                restTemplate.exchange(url,
                        HttpMethod.POST, requestEntity, new ParameterizedTypeReference<List<CatalogueItem>>() {
                        });

        return response.getBody();
    }

    public boolean callCataloguePlaceBid(Long aid, int bidAmount){
        System.out.println(aid);
        UpdateAuctionRequest updateRequest = new UpdateAuctionRequest();
        updateRequest.setAid(aid);
        updateRequest.setBid(bidAmount);
        String url = "http://localhost:8084/api/catalogue/place-bid";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<UpdateAuctionRequest> requestEntity = new HttpEntity<>(updateRequest, headers);
        ResponseEntity<Boolean> response = restTemplate.postForEntity(url, requestEntity, Boolean.class);

        return response.getBody();
    }

    public Long fetchUid(String username){
        String url = "http://localhost:8081/api/fetch-uid";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(username, headers);
        ResponseEntity<Long> response = restTemplate.postForEntity(url, requestEntity, Long.class);

        return response.getBody();
    }
}
