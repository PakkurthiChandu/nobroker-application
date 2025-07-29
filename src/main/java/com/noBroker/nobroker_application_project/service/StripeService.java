package com.noBroker.nobroker_application_project.service;

import com.stripe.exception.StripeException;

public interface StripeService {

    String createCheckoutSession(Long amount, String successUrl, String cancelUrl) throws StripeException;
}