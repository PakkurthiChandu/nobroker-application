package com.noBroker.nobroker_application_project.controller;

import com.noBroker.nobroker_application_project.model.User;
import com.noBroker.nobroker_application_project.repository.UserRepository;
import com.noBroker.nobroker_application_project.service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Random;

@Controller
public class AuthController {

    @Autowired
    private OtpService otpService;

    @Autowired
    private UserRepository userRepository;

    private final java.util.Map<String, String> otpStore = new java.util.HashMap<>();

    @GetMapping("/login")
    public String showLoginPage() {
        return "loginPage";
    }

    @PostMapping("/send-otp")
    public String sendOtp(@RequestParam("mobilePhone") String mobile, Model model) {
        String otp = String.valueOf(new Random().nextInt(900000) + 100000);
        otpService.sendOtp(mobile, otp);
        otpStore.put(mobile, otp);
        model.addAttribute("mobilePhone", mobile);
        return "verify";
    }

    @PostMapping("/verify-otp")
    public String verifyOtp(@RequestParam String mobilePhone,
                            @RequestParam String otp,
                            Model model) {
        String storedOtp = otpStore.get(mobilePhone);
        if (storedOtp != null && storedOtp.equals(otp)) {
            Optional<User> existingUser = userRepository.findByMobilePhone(mobilePhone);
            if (existingUser.isPresent()) {
                // already registered user
                return "redirect:/landing-page";
            } else {
                // first time user → only store mobile
                User user = new User();
                user.setMobilePhone(mobilePhone);
                user.setRole("USER");
                user.setIsSubscribed(false);
                userRepository.save(user);
                return "redirect:/landing-page";
            }
        }
        model.addAttribute("mobilePhone", mobilePhone);
        model.addAttribute("error", "Invalid OTP");
        return "verify";
    }
}
