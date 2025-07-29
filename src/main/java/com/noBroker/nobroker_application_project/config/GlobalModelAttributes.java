package com.noBroker.nobroker_application_project.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;

@ControllerAdvice
public class GlobalModelAttributes {

    @Value("${google.api.key}")
    private String googleApiKey;

    @ModelAttribute
    public void addGlobalAttributes(Model model) {
        model.addAttribute("googleApiKey", googleApiKey);
    }
}