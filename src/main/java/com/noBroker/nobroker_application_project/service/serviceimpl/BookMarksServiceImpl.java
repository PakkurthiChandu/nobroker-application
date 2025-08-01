package com.noBroker.nobroker_application_project.service.serviceimpl;

import com.noBroker.nobroker_application_project.model.Property;
import com.noBroker.nobroker_application_project.model.User;
import com.noBroker.nobroker_application_project.repository.PropertyRepository;
import com.noBroker.nobroker_application_project.repository.UserRepository;
import com.noBroker.nobroker_application_project.service.BookMarksService;

import org.springframework.stereotype.Service;

@Service
public class BookMarksServiceImpl implements BookMarksService {

    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;

    public BookMarksServiceImpl(PropertyRepository propertyRepository, UserRepository userRepository) {
        this.propertyRepository = propertyRepository;
        this.userRepository = userRepository;
    }

    @Override
    public boolean saveBookMarks(Long propertyId, User user) {
        Property property = propertyRepository.findById(propertyId).orElse(null);
        user = userRepository.findById(user.getUserId()).orElse(null);

        if (user != null && property != null) {
            user.getBookmarkedProperties().add(property);
            property.getBookmarkedByUsers().add(user);

            userRepository.save(user);
        }

        return true;
    }

    @Override
    public void removeBookmarks(Long propertyId, Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        Property property = propertyRepository.findById(propertyId).orElse(null);

        if (user != null && property != null) {
            user.getBookmarkedProperties().remove(property);
            property.getBookmarkedByUsers().remove(user);
        }

        userRepository.save(user);
    }
}