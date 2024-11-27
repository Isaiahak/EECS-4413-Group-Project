package com.example.BidlyCatalogue.service;
import com.example.BidlyCatalogue.api.LiveServerApi;
import com.example.BidlyCatalogue.dto.Auction;
import com.example.BidlyCatalogue.dto.Payment;
import com.example.BidlyCatalogue.dto.PaymentInfo;
import com.example.BidlyCatalogue.factory.CardTypeFactory;
import com.example.BidlyCatalogue.repo.PaymentRepo;
import com.example.BidlyCatalogue.utils.CardProcessing;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;




@Service
public class PaymentService{

    @Autowired
    private CatalogueService catalogueService;

    @Autowired
    private LiveServerApi liveServerApi;

    @Autowired
    private PaymentRepo paymentRepo;

    @Transactional
    public void addPayment(Payment payment){
        paymentRepo.save(payment);
    }

    @Transactional
    public void deletePayment(Payment payment){
        paymentRepo.delete(payment);
    }

    @Transactional
    public void updatePayment(Payment payment){
        paymentRepo.save(payment);
    }

    @Transactional
    public Payment getPayment(Payment payment){
        return paymentRepo.findById(payment.getPaymentID()).orElse(null);
    }


    public boolean processPayment(PaymentInfo paymentInfo) {
        boolean result = false;
        String cardType = getCardType(paymentInfo.getCardNumber());
        CardTypeFactory cardTypeFactory = new CardTypeFactory();
        cardTypeFactory.setCardProcessing();
        CardProcessing cardProcessing = cardTypeFactory.getCardProcessing(cardType);
        result = cardProcessing.processCard(paymentInfo.getCardNumber(), paymentInfo.getName(), paymentInfo.getExpDate(), paymentInfo.getSecurityCode());
        if (result == true) {
            Payment payment = new Payment();
            Auction auction = catalogueService.fetchAuction(paymentInfo.getAid());
            payment.setAuctionID(auction.getAid());
            payment.setFinalPrice(auction.getHighestBid());
            payment.setItemID(auction.getAid());
            addPayment(payment);
            catalogueService.removeAuction(payment.getAuctionID());
            //liveServerApi.callRemoveAuction(auction); done when the auction closes by the live server instead
            // remove subscription for the auction on auction end
            catalogueService.removeCatalogue(payment.getItemID());
            // figure out how to remove the auction for the auctions being displayed

            result = true;
        }
        System.out.println(result);
        return result;
    }

    public String getCardType(String cardNumber){
        int firstDigit = cardNumber.charAt(0);
        String cardType;
        if(firstDigit == 4){
            cardType = "Visa";
        }
        if(firstDigit == 5){
            cardType = "MasterCard";
        }
        else{
            cardType = "American Express";
        }
        return cardType;
    }

}
