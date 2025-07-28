package com.noBroker.nobroker_application_project.controller;

import com.noBroker.nobroker_application_project.model.Property;
import com.noBroker.nobroker_application_project.model.Transaction;
import com.noBroker.nobroker_application_project.model.User;
import com.noBroker.nobroker_application_project.repository.PropertyRepository;
import com.noBroker.nobroker_application_project.repository.UserRepository;
import com.noBroker.nobroker_application_project.service.PropertyService;
import com.noBroker.nobroker_application_project.service.TransactionService;
import com.noBroker.nobroker_application_project.service.UserService;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
public class ProfileController {

    private final UserService userService;
    private final PropertyRepository propertyRepository;
    private final PropertyService propertyService;
    private final UserRepository userRepository;
    private final TransactionService transactionService;

    public ProfileController(UserService userService, PropertyRepository propertyRepository,
                             PropertyService propertyService, UserRepository userRepository, TransactionService transactionService) {
        this.userService = userService;
        this.propertyRepository = propertyRepository;
        this.propertyService = propertyService;
        this.userRepository = userRepository;
        this.transactionService = transactionService;
    }

    @GetMapping("/profile/view/{userId}")
    public String viewProfile(@PathVariable("userId") Long userId,
                              HttpSession session,
                              Model model) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        model.addAttribute("user", user);

        session.setAttribute("user", user);

        return "edit-profile-details";
    }

    @PostMapping("/profile/update")
    public String updateProfile(@ModelAttribute("user") User updatedUser, RedirectAttributes redirectAttributes) {
        User user = userRepository.findById(updatedUser.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Optional<User> userByEmail = userRepository.findByEmail(updatedUser.getEmail());

        if (userByEmail.isPresent() && !userByEmail.get().getUserId().equals(user.getUserId())) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Email is already in use.");

            return "redirect:/profile/view/" + user.getUserId();
        }

        Optional<User> userByPhone = userRepository.findByMobilePhone(updatedUser.getMobilePhone());

        if (userByPhone.isPresent() && !userByPhone.get().getUserId().equals(user.getUserId())) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Mobile number is already in use.");

            return "redirect:/profile/view/" + user.getUserId();
        }

        user.setName(updatedUser.getName());
        user.setEmail(updatedUser.getEmail());
        user.setMobilePhone(updatedUser.getMobilePhone());

        userRepository.save(user);

        redirectAttributes.addFlashAttribute("successMessage",
                "Profile updated successfully.");

        return "redirect:/profile/view/" + user.getUserId();
    }

    @GetMapping("/shortlisted-properties/{userId}")
    public String showShortlisted(@PathVariable("userId") Long userId,
                                  HttpSession session,
                                  Model model) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

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
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Transaction> transactions = transactionService.getTransactionsByUserId(userId);

        model.addAttribute("transactions", transactions);
        model.addAttribute("user",user);

        return "payments";
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
