package com.noBroker.nobroker_application_project.service.serviceimpl;

import com.noBroker.nobroker_application_project.service.StripeService;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StripeServiceImpl implements StripeService {

    @Value("${stripe.secret.key}")
    private String secretKey;

    public String createCheckoutSession(Long amount, String successUrl, String cancelUrl) throws StripeException {
        SessionCreateParams params =
                SessionCreateParams.builder()
                        .setMode(SessionCreateParams.Mode.PAYMENT)
                        .setSuccessUrl(successUrl)
                        .setCancelUrl(cancelUrl)
                        .addLineItem(
                                SessionCreateParams.LineItem.builder()
                                        .setQuantity(1L)
                                        .setPriceData(
                                                SessionCreateParams.LineItem.PriceData.builder()
                                                        .setCurrency("inr")
                                                        .setUnitAmount(amount)
                                                        .setProductData(
                                                                SessionCreateParams
                                                                        .LineItem
                                                                        .PriceData
                                                                        .ProductData.builder()
                                                                        .setName("Unlock Owner Contact")
                                                                        .build()
                                                        )
                                                        .build()
                                        )
                                        .build()
                        )
                        .build();

        Session session = Session.create(params);

        return session.getUrl();
    }
}
