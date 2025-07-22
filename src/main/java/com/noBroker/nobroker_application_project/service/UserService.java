package com.noBroker.nobroker_application_project.service;

import com.noBroker.nobroker_application_project.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    User user;

    public void saveProfile(User user) {
        this.user = user;

    }




}
