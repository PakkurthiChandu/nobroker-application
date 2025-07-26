package com.noBroker.nobroker_application_project.controller;

import com.noBroker.nobroker_application_project.model.User;
import com.noBroker.nobroker_application_project.repository.UserRepository;
import com.noBroker.nobroker_application_project.service.OtpService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Random;

@Controller
public class MessageController {

    @Autowired
    private OtpService otpService;

    @Autowired
    private UserRepository userRepository;

    private String storedOtp = "";

    private final java.util.Map<String, String> otpStore = new java.util.HashMap<>();

    @GetMapping("/loginPage")
    public String showLoginPage() {
        return "loginPage";
    }

    @PostMapping("/send-otp")
    @ResponseBody
    public String sendOtp(@RequestParam("mobilePhone") String mobile, Model model) {

        String otp = String.valueOf(new Random().nextInt(900000) + 100000);
        otpService.sendOtp(mobile, otp);
        otpStore.put(mobile, otp);

        System.out.println("otp: " + otp);

        storedOtp = otp;

        model.addAttribute("mobilePhone", mobile);
        return "OTP sent";
    }

    @PostMapping("/verify-otp")
    public String verifyOtp(@RequestParam String mobilePhone,
                            @RequestParam String otp,
                            Model model, HttpServletRequest request) {

        User user = null;

        if (storedOtp != null && storedOtp.equals(otp)) {
            user = userRepository.findByMobilePhone(mobilePhone).orElse(null);

            if (user != null) {

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                user, null, List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole()))
                        );

                SecurityContextHolder.getContext().setAuthentication(authToken);

                HttpSession session = request.getSession(true);
                session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
                session.setAttribute("user", user);

                return "redirect:/landingPage";
            } else {
                user = new User();

                user.setMobilePhone(mobilePhone);
                user.setRole("USER");
                user.setIsSubscribed(false);

                user = userRepository.save(user);

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                user, null, List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole()))
                        );

                SecurityContextHolder.getContext().setAuthentication(authToken);

                HttpSession session = request.getSession(true);

                session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
                session.setAttribute("user", user);

                return "redirect:/profile/view/"+ user.getUserId();
            }
        }

        model.addAttribute("mobilePhone", mobilePhone);
        model.addAttribute("error", "Invalid OTP");

        return "verify";
    }
}