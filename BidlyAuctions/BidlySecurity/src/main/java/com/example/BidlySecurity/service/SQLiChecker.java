package com.example.BidlySecurity.service;

import com.example.BidlySecurity.dto.UserDataDTO;
import org.springframework.stereotype.Service;
import java.util.regex.*;

//SQLi check service.
@Service
public class SQLiChecker {
    //SQL injection pattern
    private static final String SQLi_PATTERN =
            "(?i)(select\\s+.*\\s+from|insert\\s+into|update" +
                    "\\s+.*set|delete\\s+from|drop\\s+table|union" +
                    "\\s+select|--|;|\\*|\\+|--|or\\s+1=1|'|" +
                    "\"|--|;|/\\*|\\*/|\\bexec\\b|\\bbenchmark" +
                    "\\b|load_file|outfile|sleep|waitfor|waitfor" +
                    "\\s+delay)";


    public boolean sqliCheck(String input){
        if(input == null || input.trim().isEmpty()){
            return false;
        }
        Pattern pattern = Pattern.compile(SQLi_PATTERN);
        Matcher matcher = pattern.matcher(input);
        return matcher.find();
    }

    public boolean verifyData(UserDataDTO data){
        for(String line : data){
            if(sqliCheck(line)){
                System.out.println("SQLI Found");
                return true;

            }
        }
        System.out.println("SQLI Not Found");
        return false;
    }
}
