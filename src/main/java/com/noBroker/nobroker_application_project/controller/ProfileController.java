package com.noBroker.nobroker_application_project.controller;

import com.noBroker.nobroker_application_project.model.Property;
import com.noBroker.nobroker_application_project.model.User;
import com.noBroker.nobroker_application_project.repository.PropertyRepository;
import com.noBroker.nobroker_application_project.repository.UserRepository;
import com.noBroker.nobroker_application_project.service.PropertyService;
import com.noBroker.nobroker_application_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;
import java.util.Set;

@Controller
public class ProfileController {

    private final UserService userService;

    private PropertyRepository propertyRepository;

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private UserRepository userRepository;

    public ProfileController(UserService userService, PropertyRepository propertyRepository) {
        this.userService = userService;
        this.propertyRepository = propertyRepository;

    }

    @GetMapping("/myprofile")
    public String basicProfile(){
        return "profile-details";
    }

    @PostMapping("/profile")
    public String profile(User user){
        userService.saveProfile(user);
        System.out.println(user.getName()+ " "+user.getEmail()+ " "+user.getMobilePhone());
        return "profile-details";
    }

    @GetMapping("/shortlisted-properties")
    public String showShortlisted(@RequestParam("userId") Long userId, Model model) {
        Set<Property> properties = propertyService.getBookmarkedPropertyDTOs(userId);
        model.addAttribute("allProperties", properties);
        return "shortlist";
    }

    @GetMapping("/your-properties")
    public String showUserProperties(@RequestParam("userId") Long userId, Model model) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            Set<Property> properties = user.get().getProperties();
            model.addAttribute("properties", properties);
        } else {
            model.addAttribute("properties", Set.of());
        }
        return "your-properties";
    }

}
