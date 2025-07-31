package com.noBroker.nobroker_application_project.service;

import com.noBroker.nobroker_application_project.model.Amenity;

public interface AmenityService {

    Amenity findOrCreateAmenity(Amenity amenity);

    void delete(Amenity amenity);
}