package com.example.BidlySignUpService.controller;
import com.example.BidlySignUpService.dto.LoginRequestDTO;
import com.example.BidlySignUpService.service.LoginService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<Boolean> verifyLogin(@RequestBody LoginRequestDTO lr){
        if(loginService.login(lr)){
            return ResponseEntity.ok(true);
        }else{
            return ResponseEntity.ok(false);
        }
    }
}
