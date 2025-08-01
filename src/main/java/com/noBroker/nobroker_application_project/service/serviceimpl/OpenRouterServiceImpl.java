package com.noBroker.nobroker_application_project.service.serviceimpl;

import com.noBroker.nobroker_application_project.service.OpenRouterService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OpenRouterServiceImpl implements OpenRouterService {

    @Value("${openrouter.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public OpenRouterServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public String getChatBotResponse(String userMessage) {
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apiKey);

        Map<String, Object> requestBody = new HashMap<>();

        requestBody.put("model", "mistralai/mistral-7b-instruct");
        requestBody.put("messages", List.of(
                Map.of("role", "system", "content",
                        "You are Natasha, an AI assistant for the NoBroker real estate website. " +
                                "Answer only property-related questions. Keep answers short (2-3 lines), " +
                                "and use bullet points with '•' symbol with new line, and avoid unnecessary info."),
                Map.of("role", "user", "content", userMessage)));

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        try {
            String API_URL = "https://openrouter.ai/api/v1/chat/completions";
            ResponseEntity<Map> response = restTemplate.postForEntity(API_URL, request, Map.class);

            Map<String, Object> choice = (Map<String, Object>) (
                    (List<?>) response.getBody().get("choices")).get(0);
            Map<String, Object> message = (Map<String, Object>) choice.get("message");

            return (String) message.get("content");
        } catch (Exception exception) {
            return "Something went wrong";
        }
    }
}