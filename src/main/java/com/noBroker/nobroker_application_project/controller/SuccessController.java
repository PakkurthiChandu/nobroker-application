package com.noBroker.nobroker_application_project.controller;

import com.noBroker.nobroker_application_project.model.Transaction;
import com.noBroker.nobroker_application_project.model.User;
import com.noBroker.nobroker_application_project.service.TransactionService;
import com.noBroker.nobroker_application_project.service.UserService;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SuccessController {

    private final UserService userService;
    private final TransactionService transactionService;

    public SuccessController(UserService userService, TransactionService transactionService) {
        this.userService = userService;
        this.transactionService = transactionService;
    }

    @GetMapping("/success")
    public String successPage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");

        Transaction transaction = new Transaction();

        transaction.setAmount(164900L);
        transaction.setPaymentStatus("SUCCESS");
        transaction.setUser(user);

        transactionService.save(transaction);

        user = userService.changeToSubscribe(user.getUserId());

        session.setAttribute("user", user);

        return "redirect:" + session.getAttribute("targetUrl").toString();
    }

    @GetMapping("/cancel")
    public String cancelPage() {
        return "cancel";
    }
}