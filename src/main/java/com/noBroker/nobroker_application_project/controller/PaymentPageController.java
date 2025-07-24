package com.noBroker.nobroker_application_project.controller;

import com.noBroker.nobroker_application_project.model.User;
import com.noBroker.nobroker_application_project.service.PropertyService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PaymentPageController {

    private final PropertyService propertyService;

    public PaymentPageController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }


    @GetMapping("/checkSubscribe")
    public  String redirectPayment(HttpSession session, Model model, @RequestParam("propertyId") Long propertyId) {
        User user = (User) session.getAttribute("user");

        System.out.println(user);

        if (user != null) {
            if (user.getIsSubscribed()) {
                model.addAttribute("owner", propertyService.getPropertyById(propertyId).getOwner());

                return "success";
            }
        }

        return "subscriptionForm";
    }
}