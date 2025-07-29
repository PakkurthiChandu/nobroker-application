package com.noBroker.nobroker_application_project.controller;

import com.noBroker.nobroker_application_project.model.Transaction;
import com.noBroker.nobroker_application_project.model.User;
import com.noBroker.nobroker_application_project.repository.TransactionRepository;
import com.noBroker.nobroker_application_project.service.PropertyService;
import com.noBroker.nobroker_application_project.service.UserService;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SuccessController {

    private final UserService userService;
    private final PropertyService propertyService;
    private final TransactionRepository transactionRepository;

    public SuccessController(UserService userService, PropertyService propertyService,
                             TransactionRepository transactionRepository) {
        this.userService = userService;
        this.propertyService = propertyService;
        this.transactionRepository = transactionRepository;
    }

    @GetMapping("/success")
    public String successPage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");

        Transaction tx = new Transaction();

        tx.setAmount(164900L);
        tx.setPaymentStatus("SUCCESS");
        tx.setUser(user);

        transactionRepository.save(tx);

        user = userService.changeToSubscribe(user.getUserId());

        session.setAttribute("user", user);

        return "redirect:" + (String) session.getAttribute("targetUrl").toString();
    }

    @GetMapping("/cancel")
    public String cancelPage() {
        return "cancel";
    }
}
