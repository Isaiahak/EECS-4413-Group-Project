package com.example.BidlySignUpService.controller;
import com.example.BidlySignUpService.service.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

//REST receiver for signup services.
//TODO: Convert this to correct rest service and request.
@Controller
public class SignUpController {

    @Autowired
    private SignUpService signUpService;

    @PostMapping("/signup")
    public ResponseEntity<Boolean> signUp(@RequestParam String username, @RequestParam String password,
                                 @RequestParam String firstName, @RequestParam String lastName,
                                 @RequestParam String street, @RequestParam String city,
                                 @RequestParam String province, @RequestParam String zipcode) {
        boolean success = signUpService.signUp(username, password, firstName, lastName, street, city, province, zipcode);
        return ResponseEntity.ok(success);
    }
}
