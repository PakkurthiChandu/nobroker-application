package com.noBroker.nobroker_application_project.service;

import com.noBroker.nobroker_application_project.model.User;

public interface BookMarksService {

    boolean saveBookMarks(Long propertyId, User user);

    void removeBookmarks(Long propertyId, Long userId);
}
