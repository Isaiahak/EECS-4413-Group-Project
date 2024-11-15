package com.example.BidlyPagesService.controller;

import com.example.BidlyPagesService.api.ApiService;
import com.example.BidlyPagesService.dto.Auction;
import com.example.BidlyPagesService.dto.LoginRequestDTO;
import com.example.BidlyPagesService.service.AuctionService;
import com.example.BidlyPagesService.service.SubscriberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PagesController {

    @Autowired
    private ApiService apiService;

    @Autowired
    private AuctionService auctionService;

    @Autowired
    private SubscriberService subscriberService;

    @GetMapping("/main")
    public String index() {
        return "main"; // Main page
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        return "signup"; // Sign-up page
    }

    @PostMapping("/signup")
    public ModelAndView handleSignUp(@RequestParam String username, @RequestParam String password,
                                     @RequestParam String firstName, @RequestParam String lastName,
                                     @RequestParam String street, @RequestParam String city,
                                     @RequestParam String province, @RequestParam String zipcode) {
        ModelAndView modelAndView = new ModelAndView();

        // Call the Signup Service with individual parameters
        boolean success = apiService.callSignUpService(username, password, firstName, lastName,
                street, city, province, zipcode);
        if (success) {
            modelAndView.setViewName("signupSuccess"); // Redirect to success page
            modelAndView.addObject("successMessage", "Sign up successful! You can now log in.");
        } else {
            modelAndView.setViewName("signup"); // Return to sign-up page with error message
            modelAndView.addObject("errorMessage", "Username is already taken.");
        }
        return modelAndView; // Return the ModelAndView object
    }

    @GetMapping("/signupSuccess")
    public String signupSuccess(Model model) {
        model.addAttribute("successMessage", "Sign up successful! You can now log in.");
        return "signupSuccess"; // Success page
    }
    @GetMapping("/login")
    public ModelAndView login(){
        return new ModelAndView("login");
    }

    @PostMapping("/login")
    public String postLogin(@RequestParam String username, @RequestParam String password){
        LoginRequestDTO lr = new LoginRequestDTO();
        lr.setPassword(password);
        lr.setUsername(username);
        boolean success = apiService.callLoginService(lr);
        if(success){
            return "redirect:/catalogue";
            //Make a request to catalogue
        }else{
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("login");
            modelAndView.addObject("errorMessage", "Login Failed");
            return "login";

        }
    }

    @GetMapping("/catalogue")
    public String getCatalogue(Model model) {

        return "catalogue";
    }

    @GetMapping("/auction")
    public String viewAuction(@RequestParam("auctionId") Long auctionId, Model model) {
        // Assuming you have a service to fetch auction details
        Auction auction = apiService.callCatalogueGetAuction(auctionId);

        // Add auction details to the model
        model.addAttribute("auction", auction);

        // Return the auction detail page view
        return "auctionSpecific"; // Make sure you have the corresponding Thymeleaf template
    }

    @PostMapping("/auction")
    public String placeBid(@RequestParam("auctionId") Long auctionId,@RequestParam("bidAmount") int bidAmount, @RequestParam String username, Model model) {
        // Assuming you have a service to fetch auction details
        Boolean bidSuccess = apiService.callCataloguePlaceBid(auctionId, bidAmount);
        subscriberService.subscribe(auctionId, username);

        // Return the auction detail page view
        return "redirect:/auction?auctionId="+auctionId; // Make sure you have the corresponding Thymeleaf template
    }

}