package com.noBroker.nobroker_application_project.service.serviceimpl;

import com.noBroker.nobroker_application_project.model.User;
import com.noBroker.nobroker_application_project.repository.UserRepository;
import com.noBroker.nobroker_application_project.service.UserService;

import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User changeToSubscribe(Long userId) {
        User user = userRepository.findById(userId).orElse(null);

        if (user != null){
            user.setIsSubscribed(true);

            return userRepository.save(user);
        }

        return null;
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findByEmailExcludeUser(String email, Long userId) {
        return  userRepository.findByEmailExcludeUser(email, userId);
    }

    @Override
    public User findByMobilePhoneExcludeUser(String mobilePhone, Long userId) {
        return  userRepository.findByMobilePhoneExcludeUser(mobilePhone, userId).orElse(null);
    }

    @Override
    public User findById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public User findByMobilePhone(String mobilePhone) {
        return userRepository.findByMobilePhone(mobilePhone).orElse(null);
    }
}