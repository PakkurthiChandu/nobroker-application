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
        System.out.println(amenity.getBathrooms());
        System.out.println(amenity.getBalcony());
        System.out.println(amenity.getWaterSupply());
        System.out.println(amenity.getPetAllowed());
        System.out.println(amenity.getGym());
        System.out.println(amenity.getNonVeg());
        System.out.println(amenity.getGatedSecurity());
        System.out.println(amenity.getLift());
        System.out.println(amenity.getGasPipeLine());
        System.out.println(amenity.getPark());
        System.out.println(amenity.getSecondaryNumber());
        System.out.println(amenity.getHouseKeeping());
        System.out.println(amenity.getInternetService());
        System.out.println(amenity.getPowerBackUp());
        System.out.println(amenity.getServentRoom());
        System.out.println(amenity.getSwimmingPool());
        System.out.println(amenity.getFireSafety());
        return "amenities-details";
    }
}