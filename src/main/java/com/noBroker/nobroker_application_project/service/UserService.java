package com.noBroker.nobroker_application_project.service;

import com.noBroker.nobroker_application_project.model.User;
import com.noBroker.nobroker_application_project.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void changeToSubscribe(Long userId) {
        User user = userRepository.findById(userId).orElse(null);

        if(user != null){
            user.setIsSubscribed(true);
            userRepository.save(user);
        }
    }

    public User findById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }
}
