package com.noBroker.nobroker_application_project.controller;

import com.noBroker.nobroker_application_project.service.StripeService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
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

        Long price = Long.valueOf(requestData.get("price").toString()); // price in paise
        String successUrl = "http://localhost:8080/success";
        String cancelUrl = "http://localhost:8080/cancel";

        return stripeService.createCheckoutSession(price, successUrl, cancelUrl);
    }
}

