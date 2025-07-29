package com.noBroker.nobroker_application_project.service;

import com.noBroker.nobroker_application_project.model.User;

public interface UserService {

    User changeToSubscribe(Long userId);

    User save(User user);

    User findByEmail(String email);

    User findByMobilePhone(String mobilePhone);

    User findById(Long userId);
}