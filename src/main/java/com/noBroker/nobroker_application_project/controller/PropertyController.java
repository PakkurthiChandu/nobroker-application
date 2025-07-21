package com.noBroker.nobroker_application_project.controller;

import com.noBroker.nobroker_application_project.dto.RentalDto;
import com.noBroker.nobroker_application_project.model.Address;
import com.noBroker.nobroker_application_project.service.PropertyService;

import com.noBroker.nobroker_application_project.model.Amenity;
import com.noBroker.nobroker_application_project.model.Property;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class PropertyController {

    PropertyService propertyService;

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

    // ---- Modification Starts here for editing -----

    @GetMapping("/edit/{propertyId}")
    public String showEditForm(@PathVariable Long propertyId,
                               HttpSession session,
                               Model theModel) {
        Property property = propertyService.getPropertyById(propertyId);
        session.setAttribute("property", property);
        theModel.addAttribute("property", property);
        return "property-details";
    }

    @PostMapping("/edit/propertyDetails")
    public String updatePropertyDetails(Property updatedProperty,
                                        HttpSession session) {
        Property property = (Property)session.getAttribute("property");
        if (property == null) {
            return "redirect:/";
        }
        property.setApartmentType(updatedProperty.getApartmentType());
        property.setApartmentName(updatedProperty.getApartmentName());
        property.setBhkType(updatedProperty.getBhkType());
        property.setFloor(updatedProperty.getFloor());
        property.setTotalFloors(updatedProperty.getTotalFloors());
        property.setPropertyAge(updatedProperty.getPropertyAge());
        property.setFacing(updatedProperty.getFacing());
        property.setBuiltUpArea(updatedProperty.getBuiltUpArea());

        propertyService.saveProperty(property);

        session.setAttribute("property", property);

        return "redirect:/edit/address";
    }

    @GetMapping("/edit/address")
    public String showEditAddressForm(HttpSession session, Model theModel) {
        Property property = (Property)session.getAttribute("property");
        if (property == null || property.getAddress() == null) {
            return "redirect:/";
        }
        theModel.addAttribute("address", property.getAddress());

        return "locality-details";
    }

    @PostMapping("/edit/address")
    public String updateAddress(@ModelAttribute Address address, HttpSession session) {
        Property property = (Property) session.getAttribute("property");
        property.setAddress(address);

        session.setAttribute("property", property);

        return "redirect:/edit/rentals";
    }

    @GetMapping("/edit/rentals")
    public String showRentalEditForm(HttpSession session, Model theModel) {
        Property property = (Property) session.getAttribute("property");
        if(property == null) {
            return "redirect:/";
        }
        theModel.addAttribute("property", property);

        return "rental-details";
    }

    @PostMapping("/edit/rentals")
    public String updateRentalDetails(@ModelAttribute Property updatedProperty,
                                      HttpSession session) {
        Property property = (Property) session.getAttribute("property");
        if(property == null) {
            return "redirect:/";
        }
        property.setAvailableFor(updatedProperty.getAvailableFor());
        property.setExpectedRent(updatedProperty.getExpectedRent());
        property.setExceptedDeposit(updatedProperty.getExceptedDeposit());
        property.setNegotiation(updatedProperty.getNegotiation());
        property.setMontlyMaintenance(updatedProperty.getMontlyMaintenance());
        property.setAvailableFrom(updatedProperty.getAvailableFrom());
        property.setPreferredTenets(updatedProperty.getPreferredTenets());
        property.setFurnishing(updatedProperty.getFurnishing());
        property.setParking(updatedProperty.getParking());
        property.setDescription(updatedProperty.getDescription());

        propertyService.saveProperty(property);

        session.setAttribute("property", property);

        System.out.println("Updating rental info for property: " + property.getPropertyId());
        System.out.println("Expected rent: " + property.getExpectedRent());

        return "redirect:/edit/amenities";
    }

    @GetMapping("/edit/amenities")
    public String showAmenityForm(HttpSession session, Model theModel) {
        Property property = (Property) session.getAttribute("property");
        if(property == null) {
            return "redirect:/";
        }
        theModel.addAttribute("amenity", property.getAmenity());

        return "amenities-details";
    }

    @PostMapping("/edit/amenities")
    public String updateAmenities(@ModelAttribute Amenity amenity, HttpSession session) {
        Property property = (Property) session.getAttribute("property");
        if(property == null) {
            return "redirect:/";
        }
        property.setAmenity(amenity);

        session.setAttribute("property", property);

        return "redirect:/edit/gallery";
    }

    @GetMapping("/edit/gallery")
    public String showGalleryEditForm(HttpSession session, Model theModel) {
        Property property = (Property) session.getAttribute("property");
        if(property == null) {
            return "redirect:/";
        }

        theModel.addAttribute("property", property);

        return "gallary";
    }

    @PostMapping("/edit/gallery")
    public String updateGallery(@RequestParam("propertyImages") MultipartFile[] propertyImages,
                                HttpSession session) {
        Property property = (Property) session.getAttribute("property");
        if(property == null) {
            return "redirect:/";
        }
        propertyService.saveImage(propertyImages);

        return "redirect:/";
    }
}