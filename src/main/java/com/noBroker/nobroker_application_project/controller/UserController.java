package com.noBroker.nobroker_application_project.controller;

import com.noBroker.nobroker_application_project.service.UserService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}/shortlists")
    public String getProperty(@PathVariable Long userId, Model model) {
        model.addAttribute("userId", userId);
        model.addAttribute("allProperties", userService.findById(userId));

        return "all-properties";
    }
}