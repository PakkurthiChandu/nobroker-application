package com.noBroker.nobroker_application_project.controller;

import com.noBroker.nobroker_application_project.model.Amenity;
import com.noBroker.nobroker_application_project.model.Property;
import com.noBroker.nobroker_application_project.service.PropertyService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PropertyController {

    private final PropertyService propertyService;

    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @GetMapping("/")
    public String getForm1(){
        return "property-details";
    }

    @PostMapping("/propertyDetails")
    public String addPropertyDetails(Property property){
       propertyService.saveProperty(property);

        return "amenities-details";
    }

    @PostMapping("/amenities")
    public String addAmenities(Amenity amenity){
        propertyService.saveAmenities(amenity);
        return "amenities-details";
    }
}