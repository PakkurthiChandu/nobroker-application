package com.noBroker.nobroker_application_project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PaymentPageController {

    @GetMapping("/subscribe")
    public  String redirectPayment(){
        return "subscriptionForm";
    }

    @GetMapping("/pay")
    public String showPaymentPage(@RequestParam Long planId, @RequestParam Long planPrice, Model model) {
        model.addAttribute("planId", planId);
        model.addAttribute("planPrice", planPrice);
        return "payment";
    }

}

