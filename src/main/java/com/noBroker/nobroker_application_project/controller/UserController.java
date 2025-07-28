package com.noBroker.nobroker_application_project.controller;

import com.noBroker.nobroker_application_project.repository.UserRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/{userId}/shortlists")
    public String getProperty(@PathVariable Long userId, Model model) {
        model.addAttribute("userId", userId);
        model.addAttribute("allProperties", userRepository.findById(userId)
                .orElse(null).getProperties());

        return "all-properties";
    }
}
