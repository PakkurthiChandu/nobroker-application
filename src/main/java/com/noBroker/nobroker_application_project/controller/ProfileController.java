package com.noBroker.nobroker_application_project.controller;

import com.noBroker.nobroker_application_project.model.Property;
import com.noBroker.nobroker_application_project.model.User;
import com.noBroker.nobroker_application_project.repository.PropertyRepository;
import com.noBroker.nobroker_application_project.repository.UserRepository;
import com.noBroker.nobroker_application_project.service.PropertyService;
import com.noBroker.nobroker_application_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller
public class ProfileController {

    private final UserService userService;

    private PropertyRepository propertyRepository;

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private UserRepository userRepository;

    public ProfileController(UserService userService, PropertyRepository propertyRepository) {
        this.userService = userService;
        this.propertyRepository = propertyRepository;

    }

    @GetMapping("/profile/view/{userId}")
    public String viewProfile(@PathVariable("userId") Long userId, Model model) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        model.addAttribute("user", user);
        return "edit-profile-details";
    }

    @PostMapping("/profile/update")
    public String updateProfile(@ModelAttribute("user") User updatedUser) {
        User user = userRepository.findById(updatedUser.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setName(updatedUser.getName());
        user.setEmail(updatedUser.getEmail());
        user.setMobilePhone(updatedUser.getMobilePhone());

        userRepository.save(user);
        return "redirect:/profile/view/" + user.getUserId();
    }


    @GetMapping("/shortlisted-properties/{userId}")
    public String showShortlisted(@PathVariable("userId") Long userId, Model model) {
        Set<Property> properties = propertyService.getBookmarkedPropertyDTOs(userId);
        User user = userRepository.findById(userId).orElse(null);

        model.addAttribute("user", user);
        model.addAttribute("allProperties", properties);
        return "shortlist";
    }

    @GetMapping("/your-properties/{userId}")
    public String showUserProperties(@PathVariable("userId") Long userId, Model model) {
        User user = userRepository.findById(userId).orElse(null);
        model.addAttribute("user", user);

        Set<Property> properties = (user != null) ? user.getProperties() : Set.of();
        model.addAttribute("properties", properties);

        return "your-properties";
    }

}
