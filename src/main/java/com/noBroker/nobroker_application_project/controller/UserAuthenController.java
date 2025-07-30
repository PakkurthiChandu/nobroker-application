package com.noBroker.nobroker_application_project.controller;

import com.noBroker.nobroker_application_project.model.User;
import com.noBroker.nobroker_application_project.service.UserService;

import jakarta.servlet.http.HttpSession;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserAuthenController {

    private final UserService userService;

    public UserAuthenController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/saveUser")
    public String saveUser(OAuth2AuthenticationToken authentication, HttpSession session) {
        String email = authentication.getPrincipal().getAttribute("email");
        String name = authentication.getPrincipal().getAttribute("name");

        User user = userService.findByEmail(email);

        if (user != null) {
            session.setAttribute("user", user);

            return "redirect:/landingPage";
        } else {
            user = new User();

            user.setName(name);
            user.setEmail(email);
            user.setRole("USER");

            user  = userService.save(user);

            session.setAttribute("user", user);
        }

        return "redirect:/profile/view/" + user.getUserId();
    }
}