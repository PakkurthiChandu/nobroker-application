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
    public String getForm1(Model theModel){
        theModel.addAttribute("property", new Property());
        theModel.addAttribute("editMode", false);

        return "property-details";
    }

    @PostMapping("/propertyDetails")
    public String addPropertyDetails(Property property, HttpSession session){
        propertyService.saveProperty(property);
        session.setAttribute("property", property);

        return "redirect:/localityDetails";
    }

    @GetMapping("/localityDetails")
    public String getFrom2(HttpSession session, Model theModel){
        Property property = (Property) session.getAttribute("property");
        if(property == null) {
            return "redirect:/";
        }

        Address address = property.getAddress();
        if(address == null) {
            address = new Address();
        }
        theModel.addAttribute("address", address);
        theModel.addAttribute("editMode", false);

        return "locality-details";
    }

    @PostMapping("/localityDetails")
    public String addAddress(@ModelAttribute Address address, HttpSession session){
        Property property = (Property) session.getAttribute("property");
        if(property == null) {
            return "redirect:/";
        }
        property.setAddress(address);
        propertyService.saveProperty(property);

        session.setAttribute("property", property);

        return "redirect:/rentalDetails";
    }

    @GetMapping("/rentalDetails")
    public String getFrom3(HttpSession session, Model theModel){
        Property property = (Property)session.getAttribute("property");
        if(property == null) {
            return "redirect:/";
        }

        RentalDto rentalDto = new RentalDto();

        rentalDto.setIsSale(property.getIsSale());
        rentalDto.setAvailableFor(property.getAvailableFor());
        rentalDto.setAvailableFrom(property.getAvailableFrom());
        rentalDto.setExpectedRent(property.getExpectedRent() != null ?
                                  property.getExpectedRent().longValue() : 0L);
        rentalDto.setExpectedDeposit(property.getExpectedDeposit() != null ?
                                      property.getExpectedDeposit().longValue() : 0L);
        rentalDto.setNegotiation(property.getNegotiation());
        rentalDto.setMonthlyMaintenance(property.getMonthlyMaintenance());
        rentalDto.setPreferredTenets(property.getPreferredTenets());
        rentalDto.setFurnishing(property.getFurnishing());
        rentalDto.setParking(property.getParking());
        rentalDto.setDescription(property.getDescription());

        theModel.addAttribute("property", property);
        theModel.addAttribute("rentalDto", rentalDto);
        theModel.addAttribute("editMode", false);

        return "rental-details";
    }

    @PostMapping("/rentalDetails")
    public String addRentalDetails(@ModelAttribute RentalDto rentalDto, HttpSession session) {
        Property property = (Property)session.getAttribute("property");
        if(property == null) {
            return "redirect:/";
        }
        property.setIsSale(rentalDto.getIsSale());
        property.setAvailableFor(rentalDto.getAvailableFor());
        property.setAvailableFrom(rentalDto.getAvailableFrom());
        property.setExpectedRent(rentalDto.getExpectedRent());
        property.setExpectedDeposit(rentalDto.getExpectedDeposit());
        property.setNegotiation(rentalDto.getNegotiation());
        property.setMonthlyMaintenance(rentalDto.getMonthlyMaintenance());
        property.setPreferredTenets(rentalDto.getPreferredTenets());
        property.setFurnishing(rentalDto.getFurnishing());
        property.setParking(rentalDto.getParking());
        property.setDescription(rentalDto.getDescription());

        propertyService.saveProperty(property);

        return "redirect:/amenities";
    }

    @GetMapping("/amenities")
    public String showAmenityForm(HttpSession session, Model theModel) {
        Property property = (Property) session.getAttribute("property");
        if(property == null) {
            return "redirect:/";
        }
        if(property.getAmenity() == null) {
            property.setAmenity(new Amenity());
        }
        theModel.addAttribute("amenity", property.getAmenity());
        theModel.addAttribute("editMode", false);

        return "amenities-details";
    }

    @PostMapping("/amenities")
    public String addAmenities(@ModelAttribute Amenity amenity, HttpSession session){
        Property property = (Property) session.getAttribute("property");
        if(property == null) {
            return "redirect:/";
        }

        property.setAmenity(amenity);
        propertyService.saveProperty(property);
        session.setAttribute("property", property);

        return "gallery";
    }

    @GetMapping("/images")
    public String propertyImages(Model theModel, HttpSession session) {
        theModel.addAttribute("editMode", session.getAttribute("property") != null);

        return "redirect:/gallery";
    }

    @PostMapping("/images")
    public String uploadImages(@RequestParam("propertyImages") MultipartFile[] propertyImages,
                               HttpSession session) {
        Property property = (Property)session.getAttribute("property");
        if(property == null) {
            return "redirect:/";
        }
        propertyService.saveImage(property, propertyImages);

        session.removeAttribute("property");

        return "redirect:/";
    }

    // ---- Modification Starts here for editing -----

    @GetMapping("/edit/{propertyId}")
    public String showEditForm(@PathVariable Long propertyId,
                               HttpSession session,
                               Model theModel) {
        Property property = propertyService.getPropertyById(propertyId);
        session.setAttribute("property", property);
        theModel.addAttribute("property", property);
        theModel.addAttribute("editMode", true);
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
        theModel.addAttribute("editMode", true);

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

        RentalDto rentalDto = new RentalDto();

        rentalDto.setIsSale(property.getIsSale());
        rentalDto.setAvailableFor(property.getAvailableFor());
        rentalDto.setAvailableFrom(property.getAvailableFrom());
        rentalDto.setExpectedRent(property.getExpectedRent());
        rentalDto.setExpectedDeposit(property.getExpectedDeposit());
        rentalDto.setNegotiation(property.getNegotiation());
        rentalDto.setMonthlyMaintenance(property.getMonthlyMaintenance());
        rentalDto.setPreferredTenets(property.getPreferredTenets());
        rentalDto.setFurnishing(property.getFurnishing());
        rentalDto.setParking(property.getParking());
        rentalDto.setDescription(property.getDescription());

        theModel.addAttribute("rentalDto", rentalDto);
        theModel.addAttribute("editMode", true);

        return "rental-details";
    }

    @PostMapping("/edit/rentals")
    public String updateRentalDetails(@ModelAttribute RentalDto rentalDto,
                                      HttpSession session) {
        Property property = (Property) session.getAttribute("property");
        if(property == null) {
            return "redirect:/";
        }
        property.setIsSale(rentalDto.getIsSale());
        property.setAvailableFor(rentalDto.getAvailableFor());
        property.setExpectedRent(rentalDto.getExpectedRent());
        property.setExpectedDeposit(rentalDto.getExpectedDeposit());
        property.setNegotiation(rentalDto.getNegotiation());
        property.setMonthlyMaintenance(rentalDto.getMonthlyMaintenance());
        property.setAvailableFrom(rentalDto.getAvailableFrom());
        property.setPreferredTenets(rentalDto.getPreferredTenets());
        property.setFurnishing(rentalDto.getFurnishing());
        property.setParking(rentalDto.getParking());
        property.setDescription(rentalDto.getDescription());

        propertyService.saveProperty(property);

        session.setAttribute("property", property);

        return "redirect:/edit/amenities";
    }

    @GetMapping("/edit/amenities")
    public String showEditAmenityForm(HttpSession session, Model theModel) {
        Property property = (Property)session.getAttribute("property");
        if(property == null) {
            return "redirect:/";
        }
        if(property.getAmenity() == null) {
            property.setAmenity(new Amenity());
        }

        theModel.addAttribute("amenity", property.getAmenity());
        theModel.addAttribute("editMode", true);

        return "amenities-details";
    }

    @PostMapping("/edit/amenities")
    public String updateAmenities(@ModelAttribute Amenity amenity, HttpSession session) {
        Property property = (Property) session.getAttribute("property");
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
        theModel.addAttribute("editMode", true);

        return "gallery";
    }

    @PostMapping("/edit/gallery")
    public String updateGallery(@RequestParam("propertyImages") MultipartFile[] propertyImages,
                                HttpSession session) {
        Property property = (Property) session.getAttribute("property");
        if(property == null) {
            return "redirect:/";
        }
        Property dbProperty = propertyService.findById(property.getPropertyId());
        if(dbProperty == null) {
            return "redirect:/";
        }
        propertyService.saveImage(dbProperty, propertyImages);

        session.removeAttribute("property");

        return "redirect:/";
    }
}