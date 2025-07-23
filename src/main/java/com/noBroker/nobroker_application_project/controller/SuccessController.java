package com.noBroker.nobroker_application_project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SuccessController {
    @GetMapping("/success")
    public String successPage() {
        return "success";
    }

    @GetMapping("/cancel")
    public String cancelPage() {
        return "cancel";
    }
}
