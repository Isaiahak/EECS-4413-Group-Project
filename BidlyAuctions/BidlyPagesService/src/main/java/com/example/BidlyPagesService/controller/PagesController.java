package com.example.BidlyPagesService.controller;

import com.example.BidlyPagesService.api.ApiService;
import com.example.BidlyPagesService.dto.Auction;
import com.example.BidlyPagesService.dto.LoginRequestDTO;
import com.example.BidlyPagesService.service.SubscriberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

//Main Controller for Bidly Auctions
//This is the Controller Component defined in system architecture.
@Controller
@SessionAttributes("uid")
public class PagesController {

    @Autowired
    private ApiService apiService;

    //Subscriber service to track user sessions.
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
    public ModelAndView handleSignUp(@RequestParam String username, @RequestParam String password, @RequestParam String firstName, @RequestParam String lastName, @RequestParam String street, @RequestParam String city, @RequestParam String province, @RequestParam String zipcode) {
        //May not be required
        ModelAndView modelAndView = new ModelAndView();

        //REST call to SignUp Microservice to initiate and complete the signup process
        boolean success = apiService.callSignUpService(username, password, firstName, lastName, street, city, province, zipcode);

        //For this project, the "Username is already taken" message will display if that username already exists
        //or if there is a positive on SQLi check.
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
    public String postLogin(@RequestParam String username, @RequestParam String password, Model model, RedirectAttributes redirectAttributes){
        //Create LoginRequestDTO for REST call to SignUpService
        LoginRequestDTO lr = new LoginRequestDTO();
        lr.setPassword(password);
        lr.setUsername(username);

        //REST Call to SignUpService to complete login process
        String uid = apiService.callLoginService(lr);

        //Login fails if there is no match between username or password, and if positive on SQLi check.
        if(uid != null){
            model.addAttribute("uid", uid);
            return "redirect:/catalogue";
        }else{
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("login");
            modelAndView.addObject("errorMessage", "Login Failed");
            return "login";

        }
    }

    @GetMapping("/catalogue")
    public String getCatalogue(Model model) {
        String uid = (String) model.asMap().get("uid");
        model.addAttribute("uid", uid);
        return "catalogue";
    }

    //Get mapping for when user selects place bid in the catalogue page.
    @GetMapping("/auction")
    public String viewAuction(@RequestParam("auctionId") Long auctionId, Model model) {

        //REST call to retrieve selected auction information
        //This information will be used by this microservice to generate
        //the auction specific page with the correct information.
        Auction auction = apiService.callCatalogueGetAuction(auctionId);
        String uid = (String) model.asMap().get("uid");
        model.addAttribute("uid",uid);
        model.addAttribute("auction", auction);

        return "auctionSpecific";
    }


    //Post mapping for when user selects place bid button.
    @PostMapping("/auction")
    public String placeBid(@RequestParam("auctionId") Long auctionId,@RequestParam("bidAmount") int bidAmount, @RequestParam String username, Model model) {

        //REST call to catalogue to process and update the placed bid.
        Boolean bidSuccess = apiService.callCataloguePlaceBid(auctionId, bidAmount);

        //Once a users bid is successful, they are now subscribed to that auction.
        subscriberService.subscribe(auctionId, username);

        return "redirect:/auction?auctionId="+auctionId;
    }

}