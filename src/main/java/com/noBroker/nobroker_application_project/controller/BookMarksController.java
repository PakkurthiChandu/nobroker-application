package com.noBroker.nobroker_application_project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BookMarksController {

    @GetMapping("/getChat")
    public String getChat() {
        return "chat";
    }
}
