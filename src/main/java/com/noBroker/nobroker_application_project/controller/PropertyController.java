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

import java.util.List;

@Controller
public class PropertyController {

    PropertyService propertyService;

    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @GetMapping("/")
    public String getForm1() {
        return "property-details";
    }

    @PostMapping("/propertyDetails")
    public String addPropertyDetails(Property property) {
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
        return "rentaldetails";
    }

    @PostMapping("/rentalDetails")
    public String addAddress(RentalDto rentalDto) {
        propertyService.saveRentails(rentalDto);

        return "amenities-details";
    }

    @PostMapping("/amenities")
    public String addAmenities(Amenity amenity){
        propertyService.saveAmenities(amenity);

        return "gallary";
    }

    @GetMapping("/images")
    public String propertyImages(Model model) {
        return "gallary";
    }

    @PostMapping("/images")
    public String uploadImages(@RequestParam("propertyImages") MultipartFile[] propertyImages) {
        propertyService.saveImage(propertyImages);

        return "/";
    }

    @GetMapping("/landingPage")
    public String getLandingPage(Model model) {
        return "landing-page";
    }

    @GetMapping("/getProperties")
    public String getPropertyDetails(
                            @RequestParam("type") String isSaleStr,
                            @RequestParam("city") String city,
                            @RequestParam("query") String searchKeyword,
                            @RequestParam(required = false) List<Integer> bhkType,
                            @RequestParam(required = false) String propertyStatus,
                            @RequestParam(required = false) List<String> furnishing,
                            @RequestParam(required = false) List<String> propertyType,
                            @RequestParam(required = false) List<String> parking,
                            @RequestParam(required = false) Boolean newBuilderProject,
                            Model model) {
        boolean isSale = "buy".equalsIgnoreCase(isSaleStr);

        model.addAttribute("allProperties", propertyService.getAllProperties(isSale, city, searchKeyword,
                bhkType,propertyStatus,furnishing,propertyType,parking
        ));

        model.addAttribute("isSale", isSaleStr);
        model.addAttribute("searchKeyword", searchKeyword);
        model.addAttribute("bhkType", bhkType);
        model.addAttribute("city", city);

        return "AllProperties";
    }
}