package com.example.BidlyCatalogue.factory;

import com.example.BidlyCatalogue.utils.CardProcessing;
import com.example.BidlyCatalogue.utils.CardProcessing.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;

public class CardTypeFactory {

    @Autowired
    private HashMap<String, CardProcessing> cardProcessingMap = new HashMap<String, CardProcessing>();

    public void setCardProcessing() {
        cardProcessingMap.put("Visa", new VisaProcessing());
        cardProcessingMap.put("Mastercard", new MasterCardProcessing());
        cardProcessingMap.put("American Express", new AmexProcessing());
    }

    public CardProcessing getCardProcessing(String cardType) {
        return cardProcessingMap.get(cardType);
    }







}
