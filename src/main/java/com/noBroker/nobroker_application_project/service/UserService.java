package com.noBroker.nobroker_application_project.service;

import com.noBroker.nobroker_application_project.model.User;

public interface UserService {

    User changeToSubscribe(Long userId);

    User save(User user);

    User findByEmailExcludeUser(String email, Long userId);

    User findByMobilePhoneExcludeUser(String mobilePhone, Long userId);

    User findById(Long userId);

    User findByEmail(String email);

    User findByMobilePhone(String mobilePhone);
}