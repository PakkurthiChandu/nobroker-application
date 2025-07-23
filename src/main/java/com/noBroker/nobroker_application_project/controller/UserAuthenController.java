package com.noBroker.nobroker_application_project.controller;

import com.noBroker.nobroker_application_project.model.User;
import com.noBroker.nobroker_application_project.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserAuthenController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/saveUser")
    public String saveUser(OAuth2AuthenticationToken authentication, Model model, HttpSession session, HttpServletRequest request) {
        String email = authentication.getPrincipal().getAttribute("email");
        String name = authentication.getPrincipal().getAttribute("name");

        User user = userRepository.findByEmail(email).orElse(null);

        System.out.println("coming google");

        if (user != null) {
            session.setAttribute("user", user);
            model.addAttribute("user", user);

            return "redirect:/landingPage";
        } else {
            user = new User();

            user.setName(name);
            user.setEmail(email);
            user.setIsSubscribed(false);
            user.setRole("USER");

            user  = userRepository.save(user);

            session.setAttribute("user", user);
            model.addAttribute("user", user);
        }

        return "redirect:/profile/view/" + user.getUserId();
    }

    @GetMapping("/checkRoles")
    @ResponseBody
    public String checkUserRoles(Authentication authentication) {
        return "Roles: " + authentication.getAuthorities();
    }
}
