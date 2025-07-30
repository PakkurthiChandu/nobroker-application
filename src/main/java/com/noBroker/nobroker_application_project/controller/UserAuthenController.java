package com.noBroker.nobroker_application_project.controller;

import com.noBroker.nobroker_application_project.model.Property;
import com.noBroker.nobroker_application_project.model.Transaction;
import com.noBroker.nobroker_application_project.model.User;
import com.noBroker.nobroker_application_project.service.PropertyService;
import com.noBroker.nobroker_application_project.service.TransactionService;
import com.noBroker.nobroker_application_project.service.UserService;

import jakarta.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserAuthenController {

    private final PropertyService propertyService;
    private final UserService userService;
    private final TransactionService transactionService;

    public UserAuthenController(PropertyService propertyService, UserService userService,
                                TransactionService transactionService) {
        this.propertyService = propertyService;
        this.userService = userService;
        this.transactionService = transactionService;
    }

    @GetMapping("/saveUser")
    public String saveUser(OAuth2AuthenticationToken authentication, Model model, HttpSession session) {
        String email = authentication.getPrincipal().getAttribute("email");
        String name = authentication.getPrincipal().getAttribute("name");

        User user = userService.findByEmail(email);

        if (user != null) {
            session.setAttribute("user", user);
            model.addAttribute("user", user);

            return "redirect:/landingPage";
        } else {
            user = new User();

            user.setName(name);
            user.setEmail(email);
            user.setIsSubscribed(false);
            user.setRole("USER");

            user  = userService.save(user);

            session.setAttribute("user", user);
            model.addAttribute("user", user);
        }

        return "redirect:/profile/view/" + user.getUserId();
    }

    @GetMapping("/checkRoles")
    @ResponseBody
    public String checkUserRoles(Authentication authentication) {
        return "Roles: " + authentication.getAuthorities();
    }

    @GetMapping("/subscribeOrNot")
    @ResponseBody
    public Map<String, Boolean> checkSubscription(HttpSession session) {
        User user = (User) session.getAttribute("user");

        Boolean isSubscriptionValid = isSubscriptionValid(user);

        Map<String, Boolean> response = new HashMap<>();

        response.put("subscribed", user != null && !user.getTransactions().isEmpty() && isSubscriptionValid);

        return response;
    }

    @GetMapping("/subscriptionForm")
    public  String subscribe(HttpSession session) {
        session.setAttribute("tagetUrl", "/landingPage");

        User user = (User) session.getAttribute("user");

        if(user == null || user.getTransactions().isEmpty()) {
            return "subscriptionForm";
        } else {
            return "subscriptionExpired";
        }
    }

    @GetMapping("/checkSubscribe")
    public  String redirectPayment(HttpSession session, Model model, @RequestParam("propertyId") Long propertyId) {
        User user = (User) session.getAttribute("user");

        session.setAttribute("tagetUrl", "/getOwnerDetails");
        session.setAttribute("propertyId", propertyId);

        if(user == null || user.getTransactions().isEmpty()) {
            return "subscriptionForm";
        } else if(!isSubscriptionValid(user)) {
            return "subscriptionExpired";
        } else {
            return "redirect:/getOwnerDetails";
        }
    }

    @GetMapping("/getOwnerDetails")
    public String getOwnerDetails(HttpSession session,Model model) {
        Long propertyId = (Long) session.getAttribute("propertyId");

        Property property = propertyService.getPropertyById(propertyId);

        model.addAttribute("owner", property.getOwner());

        return "success";
    }

    public boolean isSubscriptionValid(User user) {
        Transaction transaction = transactionService.findTopByUserOrderByPaymentTimeDesc(user);

        if (transaction != null) {
            if ("SUCCESS".equalsIgnoreCase(transaction.getPaymentStatus())) {
                LocalDateTime paymentTime = transaction.getPaymentTime();

                return paymentTime.plusMonths(1).isAfter(LocalDateTime.now());
            }
        }

        return false;
    }

    @GetMapping("/getSubscriptionForm")
    public String getSubscriptionPage(HttpSession session) {
        return "subscriptionForm";
    }
}