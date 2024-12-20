package com.example.BidlyCatalogue.api;
import com.example.BidlyCatalogue.dto.CatalogueItem;
import com.example.BidlyCatalogue.dto.UpdateAuctionRequest;
import com.example.BidlyCatalogue.dto.Auction;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

//REST api to send events to live server.
@Service
public class LiveServerApi {

    private final RestTemplate restTemplate;

    public LiveServerApi(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    //REST call to inform live server that a new auction has been added.
    public void callLiveServerAddAuction(CatalogueItem catalogueItem) {
        String url = "http://localhost:8086/api/live/new-auction";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CatalogueItem> requestEntity = new HttpEntity<>(catalogueItem, headers);
        restTemplate.postForEntity(url, requestEntity, Boolean.class);
        System.out.println("Request successful");
    }

    //REST call to inform the live server that a user has placed a bid on an auction.
    public void callLiveServerUpdateBid(UpdateAuctionRequest updateAuctionRequest) {
        String url = "http://localhost:8086/api/live/update-bid";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<UpdateAuctionRequest> requestEntity = new HttpEntity<>(updateAuctionRequest, headers);
        restTemplate.postForEntity(url, requestEntity, Boolean.class);

    }
}
