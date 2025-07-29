package com.noBroker.nobroker_application_project.controller;

import com.noBroker.nobroker_application_project.service.StripeService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Value("${stripe.secret.key}")
    private String stripeSecretKey;

    private final StripeService stripeService;

    public PaymentController(StripeService stripeService) {
        this.stripeService = stripeService;
    }

    @PostMapping("/create-checkout-session")
    public String createCheckoutSession(@RequestBody Map<String, Object> requestData) throws StripeException {
        Stripe.apiKey = stripeSecretKey;

        Long price = Long.valueOf(requestData.get("price").toString());
        String successUrl = "https://nobroker-application-3.onrender.com/success";
        String cancelUrl = "https://nobroker-application-3.onrender.com/cancel";

        return stripeService.createCheckoutSession(price, successUrl, cancelUrl);
    }
}