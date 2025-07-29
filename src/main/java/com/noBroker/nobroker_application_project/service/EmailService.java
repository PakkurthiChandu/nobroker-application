package com.noBroker.nobroker_application_project.service;

public interface EmailService {

    void sendSubscriptionExpiredEmail(String toEmail, String name);
}

