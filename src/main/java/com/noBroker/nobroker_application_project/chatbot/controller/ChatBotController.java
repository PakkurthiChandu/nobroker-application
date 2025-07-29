package com.noBroker.nobroker_application_project.chatbot.controller;

import com.noBroker.nobroker_application_project.chatbot.service.OpenRouterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/chatbot")
public class ChatBotController {

    private final OpenRouterService openRouterService;

    public ChatBotController(OpenRouterService openRouterService) {
        this.openRouterService = openRouterService;
    }

    @PostMapping
    public ResponseEntity<String> chat(@RequestBody Map<String, String> request) {
        String userMessage = request.get("message");
        String reply = openRouterService.getChatBotResponse(userMessage);

        return ResponseEntity.ok(reply);
    }
}