package com.noBroker.nobroker_application_project.service;

import com.noBroker.nobroker_application_project.model.User;
import com.noBroker.nobroker_application_project.repository.UserRepository;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User changeToSubscribe(Long userId) {
        User user = userRepository.findById(userId).orElse(null);

        if(user != null){
            user.setIsSubscribed(true);

            return userRepository.save(user);
        }

        return null;
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public User findByEmail(String email) {
        return  userRepository.findByEmail(email).orElse(null);
    }
}