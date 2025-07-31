package com.noBroker.nobroker_application_project.controller;

import com.noBroker.nobroker_application_project.model.Property;
import com.noBroker.nobroker_application_project.model.Transaction;
import com.noBroker.nobroker_application_project.model.User;
import com.noBroker.nobroker_application_project.service.PropertyService;
import com.noBroker.nobroker_application_project.service.TransactionService;
import com.noBroker.nobroker_application_project.service.UserService;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {

    private final UserService userService;
    private final PropertyService propertyService;
    private final TransactionService transactionService;

    public UserController(UserService userService, PropertyService propertyService,
                          TransactionService transactionService) {
        this.userService = userService;
        this.propertyService = propertyService;
        this.transactionService = transactionService;
    }

    @GetMapping("/{userId}/shortlists")
    public String getProperty(@PathVariable Long userId, Model model) {
        model.addAttribute("userId", userId);
        model.addAttribute("allProperties", userService.findById(userId));

        return "all-properties";
    }

    @GetMapping("/subscribeOrNot")
    @ResponseBody
    public Map<String, Boolean> checkSubscription(HttpSession session) {
        User user = (User) session.getAttribute("user");

        Boolean isSubscriptionValid = isSubscriptionValid(user);

        Map<String, Boolean> response = new HashMap<>();

        response.put("subscribed", user != null && isSubscriptionValid);

        return response;
    }

    @GetMapping("/subscriptionForm")
    public  String subscribe(HttpSession session) {
        session.setAttribute("targetUrl", "/landingPage");

        User user = (User) session.getAttribute("user");

        if(user == null || user.getTransactions().isEmpty()) {
            return "subscriptionForm";
        } else {
            return "subscriptionExpired";
        }
    }

    @GetMapping("/checkSubscribe")
    public  String redirectPayment(HttpSession session, @RequestParam("propertyId") Long propertyId) {
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