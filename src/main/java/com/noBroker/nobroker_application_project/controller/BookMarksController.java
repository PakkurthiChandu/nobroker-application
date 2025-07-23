package com.noBroker.nobroker_application_project.controller;

import com.noBroker.nobroker_application_project.model.User;
import com.noBroker.nobroker_application_project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class BookMarksController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/my-bookmarks")
    public String myBookmarks(Model model, Principal principal) {
        String email = principal.getName();
        User user = userRepository.findByEmail(email).orElse(null);

        model.addAttribute("bookmarkedProperties", user.getBookmarkedProperties());
        return "dummy"; // bookmarks.html
    }

}
