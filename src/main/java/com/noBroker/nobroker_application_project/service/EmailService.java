package com.noBroker.nobroker_application_project.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendSubscriptionExpiredEmail(String toEmail, String name) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(toEmail);
        message.setSubject("Subscription Expired");
        message.setText("Hi " + name + ",\n\nYour subscription has expired. " +
                "Please renew to access premium features.\n\nThanks,\nNoBroker Team");

        mailSender.send(message);
    }
}

