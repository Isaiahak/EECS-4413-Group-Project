package com.example.BidlyPagesService.api;
import com.example.BidlyPagesService.dto.Auction;
import com.example.BidlyPagesService.dto.LoginRequestDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

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

        HttpEntity<LoginRequestDTO> requestEntity = new HttpEntity<>(lr, headers);
        ResponseEntity<Boolean> response = restTemplate.postForEntity(url, requestEntity, Boolean.class);

        return response.getBody();
    }

    public boolean callCatalogueAddAuction(Auction auction){
        String url = "http://localhost:8084/api/catalogue/add-auction";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Auction> requestEntity = new HttpEntity<Auction>(auction, headers);
        ResponseEntity<Boolean> respose = restTemplate.postForEntity(url, requestEntity, Boolean.class);

        return respose.getBody();
    }

    public Auction callCatalogueGetAuction(Long aid){
        String url = "http://localhost:8084/api/catalogue/fetch-auction";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Long> requestEntity = new HttpEntity<>(aid, headers);
        ResponseEntity<Auction> response = restTemplate.postForEntity(url, requestEntity, Auction.class);

        return response.getBody();
    }
}
