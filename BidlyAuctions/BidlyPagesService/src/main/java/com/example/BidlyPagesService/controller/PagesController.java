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
        model.addAttribute("uid", uid);
<<<<<<< HEAD
=======
        System.out.println("we got to this part get map");
>>>>>>> 5038a01 (added the shipping date)
        model.addAttribute("auction", auction);

        return "auctionSpecific";
    }


    //Post mapping for when user selects place bid button.
    @PostMapping("/auction")
    public String placeBid(@RequestParam("auctionId") Long auctionId, @RequestParam("bidAmount") int bidAmount, Model model) {
        String uid = (String) model.asMap().get("uid");
        model.addAttribute("uid", uid);
        System.out.println(uid);
        //REST call to catalogue to process and update the placed bid.
        boolean bidSuccess = apiService.callCataloguePlaceBid(auctionId, bidAmount, uid);
        if (bidSuccess)
        //Once a users bid is successful, they are now subscribed to that auction.
            subscriberService.subscribe(auctionId,uid);

        return "redirect:/auction?auctionId="+auctionId;
    }

    @GetMapping("/dutch-auction")
<<<<<<< HEAD
    public String display(@RequestParam("auctionId") Long auctionId, Model model){
        String uid = (String) model.asMap().get("uid");
        model.addAttribute("uid", uid);
        model.addAttribute("auction", auction);
        return "/dutch-auction"
    }

    @PostMapping("/dutch-auction")
    public String buyout(){
        apiService.removeDutch(auctionid);
        return "redirect:/payment";
=======
    public String viewDutchAuction(@RequestParam("auctionId") Long auctionId, Model model) {

        //REST call to retrieve selected auction information
        //This information will be used by this microservice to generate
        //the auction specific page with the correct information.
        Auction auction = apiService.callCatalogueGetAuction(auctionId);
        String uid = (String) model.asMap().get("uid");
        model.addAttribute("uid", uid);
        System.out.println("we got to this part get map");
        model.addAttribute("auction", auction);

        return "dutchAuctionSpecific";
    }


    //Post mapping for when user selects place bid button.
    @PostMapping("/dutch-auction")
    public String buyout(@RequestParam("auctionId") Long auctionId, Model model) {
        String uid = (String) model.asMap().get("uid");
        model.addAttribute("uid", uid);
        System.out.println(uid);
        //REST call to catalogue to process and update the placed bid.
        boolean buyout = apiService.callCatalogueBuyout(auctionId, uid);

        return "redirect:/auction-results?auctionId="+auctionId;
>>>>>>> 5038a01 (added the shipping date)
    }

    @GetMapping("/auction-results")
    public String getAuctionResults(@RequestParam("auctionId") Long aid, Model model, RedirectAttributes redirectAttributes){
        String auctionEndRedirect;
        String uid = (String) model.asMap().get("uid");
        model.addAttribute("uid", uid);
        System.out.println(uid);
        //return "auction-results";String

        Auction auctionResults = apiService.callCatalogueGetAuction(aid);
        System.out.println(auctionResults.getUserid() + " " + uid);
        if (auctionResults.getUserid().compareTo(uid) == 0) {
            auctionEndRedirect = "redirect:/auction-over-winner";
            redirectAttributes.addFlashAttribute("auction", auctionResults);
            System.out.println(auctionResults.getAid());
        }
        else {
            auctionEndRedirect = "redirect:/auction-over";
        }
        return auctionEndRedirect;

    }

    @GetMapping("/auction-over-winner")
    public String setupAuctionEndDisplay(Model model){
        Auction auction = (Auction)model.asMap().get("auction");
        System.out.println("Auction over winner get" + auction.getAid());
        CatalogueItem item = apiService.callGetACatalogueItem(auction.getAid());
        String itemDescription = item.getTitle();
        double shippingPrice = item.getShippingPrice();
        double expeditedShipping = item.getExpeditedShipping();
        double winningPrice = auction.getHighestBid();
        String highestBidder = auction.getUserid();
        model.addAttribute("ItemDescription",itemDescription);
        model.addAttribute("ShippingPrice", shippingPrice);
        model.addAttribute("ExpeditedShipping", expeditedShipping);
        model.addAttribute("WinningPrice", winningPrice);
        model.addAttribute("HighestBidder", highestBidder);
        model.addAttribute("auction", auction);
        return "auction-over-winner";
    }

    @PostMapping("/auction-over-winner")
    public String setupPayNow(Model model, @RequestParam("shipping") String shippingtype,
                             // @RequestParam(value = "action", required = false) String action,
                              @RequestParam("ExpeditedShipping") double expeditedShipping,
                              @RequestParam("ShippingPrice") double shippingPrice,
                              @ModelAttribute("auctionId") Long aid,
                              @ModelAttribute("WinningPrice") double winPrice,
                              @ModelAttribute("ItemDescription") String itemDescription,
                              RedirectAttributes redirectAttributes) {
        double finalPrice;
<<<<<<< HEAD
        System.out.println(aid);
=======

        System.out.println(aid);
        CatalogueItem catalogue = callGetACatalogueItem(aid);
        String shippingDate = catalogue.getShippingDate();

>>>>>>> 5038a01 (added the shipping date)
        //if ("submit".equals(action)) {
            System.out.println("submit");
            redirectAttributes.addAttribute("auctionId", aid);
            if ("expedited".equals(shippingtype)) {
                finalPrice = winPrice + expeditedShipping;
<<<<<<< HEAD
                model.addAttribute("FinalPrice", finalPrice);
                redirectAttributes.addAttribute("FinalPrice", finalPrice);
            } else if ("no-expedited".equals(shippingtype)) {
                finalPrice = winPrice + shippingPrice;
                model.addAttribute("FinalPrice", finalPrice);
=======
                Integer shippingDateInt = Integer.parseInt(shippingDate);
                shippingDate = Integer.toString(shippingDateInt / 2);
                model.addAttribute("FinalPrice", finalPrice);
                model.addAttribute("ShippingDate", shippingDate);
                redirectAttributes.addAttribute("ShippingDate", shippingDate);
                redirectAttributes.addAttribute("FinalPrice", finalPrice);

            } else if ("standard".equals(shippingtype)) {
                finalPrice = winPrice + shippingPrice;
                model.addAttribute("FinalPrice", finalPrice);
                model.addAttribute("ShippingDate", shippingDate);
                redirectAttributes.addAttribute("ShippingDate", shippingDate);
>>>>>>> 5038a01 (added the shipping date)
                redirectAttributes.addAttribute("FinalPrice", finalPrice);
            }
            System.out.println("payment");
            return "redirect:/payment";
        //}
    }



    @GetMapping("/payment")
    public String getPaymentPage(@RequestParam("auctionId") Long aid, @RequestParam("FinalPrice") double finalPrice, Model model, RedirectAttributes redirectAttributes){
        System.out.println(aid);
        String uid = (String) model.asMap().get("uid");
        model.addAttribute("uid", uid);
        UserInfo userInfo = apiService.fetchUserInfo(uid);
        String firstName = userInfo.getFirstName();
        String lastName  = userInfo.getLastName();
        String street  = userInfo.getStreet();
        String city = userInfo.getCity();
        String province = userInfo.getProvince();
        String postalCode = userInfo.getZipcode();
        model.addAttribute("finalPrice", finalPrice);
        model.addAttribute("firstName", firstName);
        model.addAttribute("lastName", lastName);
        model.addAttribute("street", street);
        model.addAttribute("city", city);
        model.addAttribute("province", province);
        model.addAttribute("postalCode", postalCode);
        model.addAttribute("auctionId", aid);

        return "payment";
    }

    @PostMapping("/payment")
    public String processPayment(Model model, @RequestParam("cardNumber") String cardNumber,
                                 @RequestParam("nameOnCard") String name,
                                 @RequestParam("expirationDate") String expDate,
                                 @RequestParam("securityCode") String securityCode,
                                 @RequestParam("auctionId") Long aid,
                                 @RequestParam("FinalPrice") Double finalPrice,
<<<<<<< HEAD
=======
                                 @RequestParam("ShippingDate") String shippingDate,
>>>>>>> 5038a01 (added the shipping date)
                                 RedirectAttributes redirectAttributes
                                 ) {
        System.out.println(aid);
        String uid = (String) model.asMap().get("uid");
        model.addAttribute("uid", uid);

        String returnString = "catalogue"; // TODO make a failed process page;
        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setCardNumber(cardNumber);
        paymentInfo.setName(name);
        paymentInfo.setExpDate(expDate);
        paymentInfo.setSecurityCode(securityCode);
        paymentInfo.setUid(uid);
        paymentInfo.setAid(aid);
        paymentInfo.setFinalPrice(finalPrice);
        boolean result = apiService.sendPaymentInfo(paymentInfo);
        if (result == true) {
            System.out.println("Receipt");
            returnString = "redirect:/receipt";
            redirectAttributes.addAttribute("auctionId", aid);
            redirectAttributes.addAttribute("FinalPrice", finalPrice);
<<<<<<< HEAD
=======
            redirectAttributes.addAttribute("ShippingDate", shippingDate);
>>>>>>> 5038a01 (added the shipping date)

        }
        System.out.println("catalogue");
        return returnString;
    }

    @GetMapping("/receipt")
<<<<<<< HEAD
    public String getReceiptInfo(@RequestParam("auctionId") Long aid,@RequestParam("FinalPrice") double finalPrice, Model model) {
=======
    public String getReceiptInfo(@RequestParam("auctionId") Long aid,@RequestParam("FinalPrice") double finalPrice,@RequestParam("ShippingDate") String shippingDate, Model model) {
>>>>>>> 5038a01 (added the shipping date)
        String uid = (String) model.asMap().get("uid");
        model.addAttribute("uid", uid);

        UserInfo userInfo = apiService.fetchUserInfo(uid);
        String firstName = userInfo.getFirstName();
        String lastName  = userInfo.getLastName();
        String street  = userInfo.getStreet();
        String city = userInfo.getCity();
        String province = userInfo.getProvince();
        String postalCode = userInfo.getZipcode();
<<<<<<< HEAD

        // TODO: Shipping address is not set when an auction is created, we need to handle that.
        // TODO: We cannot use the Catalogue item to retrieve information since the process payment method removes it.
        //String shippingDate = item.getShippingDate();
        //model.addAttribute("shippingDate", shippingDate);

=======
        model.addAttribute("shippingDate", shippingDate);
>>>>>>> 5038a01 (added the shipping date)
        model.addAttribute("firstName", firstName);
        model.addAttribute("lastName", lastName);
        model.addAttribute("street", street);
        model.addAttribute("city", city);
        model.addAttribute("province", province);
        model.addAttribute("postalCode", postalCode);
        model.addAttribute("finalPrice", finalPrice);
        model.addAttribute("itemID", aid);

        return "receipt";
    }

    @PostMapping("/receipt")
    public String backToCatalogue(){
        return "redirect:/catalogue";
    }

}