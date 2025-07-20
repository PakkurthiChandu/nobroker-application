package com.noBroker.nobroker_application_project.controller;

import com.noBroker.nobroker_application_project.dto.RentalDto;
import com.noBroker.nobroker_application_project.model.Address;
import com.noBroker.nobroker_application_project.service.PropertyService;

import com.noBroker.nobroker_application_project.model.Amenity;
import com.noBroker.nobroker_application_project.model.Property;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class PropertyController {

    PropertyService propertyService;

    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @GetMapping("/images")
    public String propertyImages(Model model) {
        return "gallary";
    }

    @PostMapping("/images")
    public String uploadImages(@RequestParam("propertyImages") MultipartFile[] propertyImages) {
        propertyService.saveImage(propertyImages);

        return "redirect:/next-step"; // change this as per your flow
    }

    @GetMapping("/")
    public String getForm1(){
        return "property-details";
    }

    @PostMapping("/propertyDetails")
    public String addPropertyDetails(Property property){
       propertyService.saveProperty(property);

        return "locality-details";
    }

    @GetMapping("/localityDetails")
    public String getFrom2(){
        return "locality-details";
    }

    @PostMapping("/localityDetails")
    public String addAddress(Address address){
        propertyService.saveAddress(address);

        return "rental-details";
    }

    @GetMapping("/rentalDetails")
    public String getFrom3(){
        return "rental-details";
    }

    @PostMapping("/rentalDetails")
    public String addAddress(RentalDto rentalDto) {
        propertyService.saveRentails(rentalDto);

        return "ammenties-details";
    }

    @PostMapping("/amenities")
    public String addAmenities(Amenity amenity){
        propertyService.saveAmenities(amenity);

        return "gallary";
    }
}