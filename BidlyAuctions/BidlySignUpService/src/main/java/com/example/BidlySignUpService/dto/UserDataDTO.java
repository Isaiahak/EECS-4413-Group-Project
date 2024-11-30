package com.example.BidlySignUpService.dto;

import java.util.ArrayList;

//User Data DTO, sent to security microservice to perform checks.
//Since the security service will be called in all user input events, it is not clear what the format of the information
//will be, therefore, all user inputs to be checked for SQLI will be sent as an arrayList.
//The reason this is here is to prevent confusion.
public class UserDataDTO extends ArrayList<String> {
}
