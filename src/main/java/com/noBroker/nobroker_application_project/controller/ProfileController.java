package com.noBroker.nobroker_application_project.controller;

import com.noBroker.nobroker_application_project.model.Property;
import com.noBroker.nobroker_application_project.model.Transaction;
import com.noBroker.nobroker_application_project.model.User;
import com.noBroker.nobroker_application_project.service.PropertyService;
import com.noBroker.nobroker_application_project.service.TransactionService;
import com.noBroker.nobroker_application_project.service.UserService;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Set;

@Controller
public class ProfileController {

    private final PropertyService propertyService;
    private final TransactionService transactionService;
    private final UserService userService;

    public ProfileController(PropertyService propertyService, TransactionService transactionService,
                             UserService userService) {
        this.propertyService = propertyService;
        this.transactionService = transactionService;
        this.userService = userService;
    }

    @GetMapping("/profile/view/{userId}")
    public String viewProfile(@PathVariable("userId") Long userId,
                              HttpSession session,
                              Model model) {
        User user = userService.findById(userId);

        model.addAttribute("user", user);

        session.setAttribute("user", user);

        return "edit-profile-details";
    }

    @PostMapping("/profile/update")
    public String updateProfile(@ModelAttribute("user") User updatedUser, RedirectAttributes redirectAttributes) {
        User user = userService.findById(updatedUser.getUserId());

        User userByEmail = userService.findByEmail(updatedUser.getEmail());

        if (userByEmail != null) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Email is already in use.");

            return "redirect:/profile/view/" + user.getUserId();
        }

       User userByPhone = userService.findByMobilePhone(updatedUser.getMobilePhone());

        if (userByPhone != null) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Mobile number is already in use.");

            return "redirect:/profile/view/" + user.getUserId();
        }

        user.setName(updatedUser.getName());
        user.setEmail(updatedUser.getEmail());
        user.setMobilePhone(updatedUser.getMobilePhone());

        userService.save(user);

        redirectAttributes.addFlashAttribute("successMessage",
                "Profile updated successfully.");

        return "redirect:/profile/view/" + user.getUserId();
    }

    @GetMapping("/shortlisted-properties/{userId}")
    public String showShortlisted(@PathVariable("userId") Long userId,
                                  HttpSession session,
                                  Model model) {
        User user = userService.findById(userId);

        Set<Property> properties = propertyService.getBookmarkedPropertyDTOs(userId);

        model.addAttribute("user", user);
        model.addAttribute("allProperties", properties);

        session.setAttribute("user", user);

        return "shortlist";
    }

    @GetMapping("/shortlisted-payments/{userId}")
    public String showPayments(@PathVariable("userId") Long userId,
                                  HttpSession session,
                                  Model model) {
        User user = userService.findById(userId);

        List<Transaction> transactions = transactionService.getTransactionsByUserId(userId);

        model.addAttribute("transactions", transactions);
        model.addAttribute("user",user);

        return "payments";
    }

    @GetMapping("/your-properties/{userId}")
    public String showUserProperties(@PathVariable("userId") Long userId, Model model) {
        User user = userService.findById(userId);

        model.addAttribute("user", user);

        Set<Property> properties = (user != null) ? user.getProperties() : Set.of();

        model.addAttribute("properties", properties);

        return "your-properties";
    }
}