package com.example.BidlyPagesService.controller;

import com.example.BidlyPagesService.api.ApiService;
import com.example.BidlyPagesService.dto.Auction;
import com.example.BidlyPagesService.dto.CatalogueItem;
import com.example.BidlyPagesService.dto.LoginRequestDTO;
import com.example.BidlyPagesService.dto.UserInfo;
import com.example.BidlyPagesService.dto.PaymentInfo;
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

    @GetMapping("/auction-results")
    public String getAuctionResults(@RequestParam("uid") long uid){
        return "auction-results";

    }


    @PostMapping("/auction-results")
    public String auctionResults(@RequestBody long aid, @RequestBody long uid, RedirectAttributes redirectAttributes) {
        String auctionEndRedirect;
        Auction auctionResults = apiService.callCatalogueGetAuction(aid);
        if (auctionResults.getUserid() != (uid)) {
            auctionEndRedirect = "redirect:/auction-over-winner";
            redirectAttributes.addAttribute("auction", auctionResults);
        } else {
            auctionEndRedirect = "redirect:/auction-over";
        }
        return auctionEndRedirect;
    }

    @GetMapping("/auction-over-winner")
    public String setupAuctionEndDisplay(Model model, @RequestParam("aid") Auction auction){
        CatalogueItem item = apiService.callGetACatalogueItem(auction.getItemid());
        String itemDescription = item.getTitle();
        double shippingPrice = item.getShippingPrice();
        double expeditedShipping = item.getExpeditedShipping();
        double winningPrice = auction.getHighestBid();
        long highestBidder = auction.getUserid();
        model.addAttribute("Item Description",itemDescription);
        model.addAttribute("Shipping Price", shippingPrice);
        model.addAttribute("Expedited Shipping", expeditedShipping);
        model.addAttribute("Winning Price", winningPrice);
        model.addAttribute("HighestBidder", highestBidder);
        model.addAttribute("auction", auction);
        return "auction-over-winner";
    }

    @PostMapping("/auction-over-winner")
    public String setupPayNow(@RequestParam("shipping") String shippingtype,
                              @RequestParam(value = "action", required = false) String action,
                              @RequestParam("Auction") Auction auction,
                              @RequestParam("Expedited Shipping") double expeditedShipping,
                              @RequestParam("Shipping Price") double shippingPrice,
                              @RequestParam("auctionId") long auctionId,
                              @RequestParam("HighestBidder") long highestBidder,
                              @RequestParam("Item Description") String itemDescription,
                              RedirectAttributes redirectAttributes) {
        double finalPrice;
        if ("submit".equals(action)) {
            redirectAttributes.addAttribute("Auction", auction);
            if ("expedited".equals(shippingtype)) {
                finalPrice = auction.getHighestBid() + expeditedShipping;
                redirectAttributes.addAttribute("Final Price", finalPrice);
            } else if ("no-expedited".equals(shippingtype)) {
                finalPrice = auction.getHighestBid() + shippingPrice;
                redirectAttributes.addAttribute("Final Price", finalPrice);
            }
            return "redirect:/payment";
        }
        return "/auction-over-winner";
    }



    @GetMapping("/payment")
    public String getPaymentPage(@RequestParam("uid") long uid,@RequestParam("auction") Auction auction, Model model){
        UserInfo userInfo = apiService.fetchUserInfo(uid);
        String firstName = userInfo.getFirstName();
        String lastName  = userInfo.getLastName();
        String street  = userInfo.getStreet();
        String city = userInfo.getCity();
        String province = userInfo.getProvince();
        String postalCode = userInfo.getZipcode();
        model.addAttribute("firstName", firstName);
        model.addAttribute("lastName", lastName);
        model.addAttribute("street", street);
        model.addAttribute("city", city);
        model.addAttribute("province", province);
        model.addAttribute("postalCode", postalCode);

        return "payment";
    }

    @PostMapping("/payment")
    public String processPayment(@RequestParam("cardNumber") String cardNumber,
                                 @RequestParam("nameOnCard") String name,
                                 @RequestParam("expirationDate") String expDate,
                                 @RequestParam("securityCode") String securityCode,
                                 @RequestParam("uid") long uid,
                                 @RequestParam("auction") Auction auction,
                                 @RequestParam("Fina lPrice") double finalPrice,
                                 RedirectAttributes redirectAttributes
                                 ) {
        String returnString = "catalogue"; // TODO make a failed process page;
        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setCardNumber(cardNumber);
        paymentInfo.setName(name);
        paymentInfo.setExpDate(expDate);
        paymentInfo.setSecurityCode(securityCode);
        paymentInfo.setUid(uid);
        paymentInfo.setAid(auction.getAid());
        paymentInfo.setFinalPrice(finalPrice);
        boolean result = apiService.sendPaymentInfo(paymentInfo);
        if (result == true) {
            returnString = "redirect:/receipt";
            redirectAttributes.addAttribute("auction", auction);
            redirectAttributes.addAttribute("Final Price", finalPrice);

        }
        return returnString;
    }

    @GetMapping("/receipt")
    public String getReceiptInfo(@RequestParam("uid") long uid,@RequestParam("auction") Auction auction,@RequestParam("Final Price") double finalPrice, Model model) {
        UserInfo userInfo = apiService.fetchUserInfo(uid);
        String firstName = userInfo.getFirstName();
        String lastName  = userInfo.getLastName();
        String street  = userInfo.getStreet();
        String city = userInfo.getCity();
        String province = userInfo.getProvince();
        String postalCode = userInfo.getZipcode();
        CatalogueItem item = apiService.callGetACatalogueItem(auction.getItemid());
        long itemID = item.getAid();
        String shippingDate = item.getShippingDate();
        model.addAttribute("firstName", firstName);
        model.addAttribute("lastName", lastName);
        model.addAttribute("street", street);
        model.addAttribute("city", city);
        model.addAttribute("province", province);
        model.addAttribute("postalCode", postalCode);
        model.addAttribute("finalPrice", finalPrice);
        model.addAttribute("itemID", itemID);
        model.addAttribute("shippingDate", shippingDate);
        return "receipt";
    }

    @PostMapping("/receipt")
    public String backToCatalogue(){
        return "redirect:/catalogue";
    }

}