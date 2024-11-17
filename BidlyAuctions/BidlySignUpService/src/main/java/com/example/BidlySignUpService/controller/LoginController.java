package com.example.BidlySignUpService.controller;
import com.example.BidlySignUpService.dto.LoginRequestDTO;
import com.example.BidlySignUpService.service.LoginService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

//REST Recieiver for login services
@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<String> verifyLogin(@RequestBody LoginRequestDTO lr){
        String uid = loginService.login(lr);
        if(uid != null){
            return ResponseEntity.ok(uid);
        }else{
            return ResponseEntity.ok(null);
        }
    }

    @PostMapping("/fetch-uid")
    public ResponseEntity<Long> fetchUid(@RequestBody String username){
        return ResponseEntity.ok(loginService.fetchUid(username));
    }

}
