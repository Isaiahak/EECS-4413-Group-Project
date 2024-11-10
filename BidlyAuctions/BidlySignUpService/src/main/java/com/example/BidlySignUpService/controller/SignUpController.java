package com.example.BidlySignUpService.controller;
import com.example.BidlySignUpService.service.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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
//
//        ModelAndView modelAndView = new ModelAndView("signup"); // Return to signup page
//        if (success) {
//            modelAndView.setViewName("signUpSuccess"); // Redirect to success page if sign up succeeded
//        } else {
//            modelAndView.addObject("errorMessage", "Username is already in use."); // Add error message for duplicate username
//        }
        return ResponseEntity.ok(success);
    }

    @GetMapping("/signup")
    public ModelAndView showSignUpForm() {
        return new ModelAndView("signup"); // This should match the name of your JSP file
    }
}
