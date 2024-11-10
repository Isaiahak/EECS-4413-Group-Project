package com.example.BidlySecurity.controller;


import com.example.BidlySecurity.dto.UserDataDTO;
import com.example.BidlySecurity.service.SQLiChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sec")
public class SecurityController {
    @Autowired
    private SQLiChecker sqli;

    @PostMapping("/sqli-check")
    public ResponseEntity<Boolean> verify(@RequestBody UserDataDTO data){
        if(sqli.verifyData(data)){
            return ResponseEntity.ok(true);
        }else{
            return ResponseEntity.ok(false);
        }
    }
}
