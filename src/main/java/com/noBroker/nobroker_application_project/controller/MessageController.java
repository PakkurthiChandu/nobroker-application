package com.noBroker.nobroker_application_project.controller;

import com.noBroker.nobroker_application_project.model.User;
import com.noBroker.nobroker_application_project.service.OtpService;
import com.noBroker.nobroker_application_project.service.UserService;

import jakarta.servlet.http.HttpSession;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Random;

@Controller
public class MessageController {

    private final OtpService otpService;
    private final UserService userService;
    private String storedOtp = "";

    public MessageController(OtpService otpService, UserService userService) {
        this.otpService = otpService;
        this.userService = userService;
    }

    @PostMapping("/send-otp")
    @ResponseBody
    public String sendOtp(@RequestParam("mobilePhone") String mobile, Model model) {
        String otp = String.valueOf(new Random().nextInt(900000) + 100000);
        storedOtp = otp;

        otpService.sendOtp(mobile, otp);

        System.out.println("otp: " + otp);

        model.addAttribute("mobilePhone", mobile);

        return "OTP sent";
    }

    @PostMapping("/verify-otp")
    public String verifyOtp(@RequestParam String mobilePhone,
                            @RequestParam String otp,
                            HttpSession session,
                            Model model) {
        User user;

        if (storedOtp != null && storedOtp.equals(otp)) {
            user = userService.findByMobilePhone(mobilePhone);

            if (user != null) {
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                user, null, List.of(new SimpleGrantedAuthority("ROLE_" +
                                user.getRole())));

                SecurityContextHolder.getContext().setAuthentication(authToken);

                session.setAttribute("user", user);
                session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

                return "redirect:/landingPage";
            } else {
                user = new User();

                user.setMobilePhone(mobilePhone);
                user.setRole("USER");
                user.setIsSubscribed(false);

                user = userService.save(user);

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                user, null, List.of(new SimpleGrantedAuthority("ROLE_" +
                                user.getRole()))
                        );

                SecurityContextHolder.getContext().setAuthentication(authToken);

                session.setAttribute("user", user);
                session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

                return "redirect:/profile/view/"+ user.getUserId();
            }
        }

        model.addAttribute("mobilePhone", mobilePhone);
        model.addAttribute("error", "Invalid OTP");

        return "verify";
    }
}