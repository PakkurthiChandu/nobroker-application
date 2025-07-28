package com.noBroker.nobroker_application_project.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class OtpService {

    @Value("${fast2sms.api.key}")
    private String apiKey;

    public void sendOtp(String mobile, String otp) {
        String url = "https://www.fast2sms.com/dev/bulkV2";

        HttpHeaders headers = new HttpHeaders();
        headers.set("authorization", apiKey);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("sender_id", "FSTSMS");
        body.add("message", "Your OTP for NoBroker login is " + otp);
        body.add("language", "english");
        body.add("route", "q");
        body.add("numbers", mobile);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(body, headers);
        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
            System.out.println("Fast2SMS response: " + response.getBody());
        } catch (Exception e) {
            System.out.println("Error sending OTP: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
