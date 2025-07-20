package com.noBroker.nobroker_application_project.service;

import com.noBroker.nobroker_application_project.model.Property;
import org.springframework.stereotype.Service;

@Service
public class PropertyService {

    Property property;
    public void  saveProperty(Property property) {
         this.property =property;
    }
}