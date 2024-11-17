package com.example.BidlyCatalogue.utils;

public class CardProcessing {

    public boolean processCard(String cardNumber, String name, String expDate, String securityCode){
        return false;
    };



    public static class VisaProcessing extends CardProcessing {

        @Override
        public boolean processCard(String cardNumber, String name, String expDate, String securityCode) {
            boolean result = true;
            int lastDigit = cardNumber.charAt(cardNumber.length());
            if (lastDigit == 7)
                result = false;

            return result;
        }
    }

    public static class MasterCardProcessing extends CardProcessing{

        @Override
        public boolean processCard(String cardNumber, String name, String expDate, String securityCode) {
            boolean result = true;
            int lastDigit = cardNumber.charAt(cardNumber.length());
            if (lastDigit == 1)
                result = false;

        return result;
        }
    }

    public static class AmexProcessing extends CardProcessing {

        @Override
        public boolean processCard(String cardNumber, String name, String expDate, String securityCode) {
            boolean result = true;
            int lastDigit = cardNumber.charAt(cardNumber.length());
            if (lastDigit == 4)
                result = false;

            return result;


        }
    }

}
