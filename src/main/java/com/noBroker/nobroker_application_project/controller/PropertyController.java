package com.noBroker.nobroker_application_project.controller;

import com.noBroker.nobroker_application_project.dto.RentalDto;
import com.noBroker.nobroker_application_project.model.Address;
import com.noBroker.nobroker_application_project.repository.PropertyRepository;
import com.noBroker.nobroker_application_project.model.User;
import com.noBroker.nobroker_application_project.service.PropertyService;

import com.noBroker.nobroker_application_project.model.Amenity;
import com.noBroker.nobroker_application_project.model.Property;

import com.noBroker.nobroker_application_project.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class PropertyController {

    UserService userService;
    private final PropertyService propertyService;
    private final PropertyRepository propertyRepository;

    public PropertyController(PropertyService propertyService, PropertyRepository propertyRepository) {
        this.propertyService = propertyService;
        this.userService = userService;
        this.propertyRepository = propertyRepository;
    }

//    ----------------Create Post----------------------------------

    @GetMapping("/welcome")
    public String welcomePage() {
        return "welcome"; // loads welcome.html from /templates
    }

    @GetMapping("/")
    public String getForm1(Model model){
        model.addAttribute("property", new Property());
        model.addAttribute("editMode", false);

        return "property-details";
    }

    @PostMapping("/propertyDetails")
    public String addPropertyDetails(Property property, HttpSession session){
        session.setAttribute("property", property);

        return "redirect:/localityDetails";
    }

    @GetMapping("/localityDetails")
    public String getFrom2(HttpSession session, Model model){
        Property property = (Property) session.getAttribute("property");
        if(property == null) {
            return "redirect:/";
        }

        Address address = property.getAddress();
        if(address == null) {
            address = new Address();
        }

        model.addAttribute("address", address);
        model.addAttribute("editMode", false);

        return "locality-details";
    }

    @PostMapping("/localityDetails")
    public String addAddress(@ModelAttribute Address address, HttpSession session){
        Property property = (Property) session.getAttribute("property");

        if(property == null) {
            return "redirect:/";
        }

        property.setAddress(address);

        session.setAttribute("property", property);


        return "redirect:/rentalDetails";
    }

    @GetMapping("/rentalDetails")
    public String getFrom3(HttpSession session, Model model){
        Property property = (Property)session.getAttribute("property");
        if(property == null) {
            return "redirect:/";
        }

        RentalDto rentalDto = new RentalDto();

        rentalDto.setIsSale(property.getIsSale());
        rentalDto.setAvailableFor(property.getAvailableFor());
        rentalDto.setAvailableFrom(property.getAvailableFrom());
        rentalDto.setExpectedRent(property.getExpectedRent() != null ?
                                  property.getExpectedRent() : 0L);
        rentalDto.setExpectedDeposit(property.getExpectedDeposit() != null ?
                                      property.getExpectedDeposit() : 0L);
        rentalDto.setNegotiation(property.getNegotiation());
        rentalDto.setMonthlyMaintenance(property.getMonthlyMaintenance());
        rentalDto.setPreferredTenets(property.getPreferredTenets());
        rentalDto.setFurnishing(property.getFurnishing());
        rentalDto.setParking(property.getParking());
        rentalDto.setDescription(property.getDescription());
        rentalDto.setPrice(property.getPrice());
        rentalDto.setPropertyStatus(property.getPropertyStatus());

        model.addAttribute("property", property);
        model.addAttribute("rentalDto", rentalDto);
        model.addAttribute("editMode", false);

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
        property.setExpectedDeposit(rentalDto.getExpectedDeposit() != null ?
                rentalDto.getExpectedDeposit() : 0L);
        property.setNegotiation(rentalDto.getNegotiation());
        property.setMonthlyMaintenance(rentalDto.getMonthlyMaintenance());
        property.setPreferredTenets(rentalDto.getPreferredTenets());
        property.setFurnishing(rentalDto.getFurnishing());
        property.setParking(rentalDto.getParking());
        property.setDescription(rentalDto.getDescription());
        property.setPrice(rentalDto.getPrice());
        property.setPropertyStatus(rentalDto.getPropertyStatus());

        session.setAttribute("property", property);

        return "redirect:/amenities";
    }

    @GetMapping("/amenities")
    public String showAmenityForm(HttpSession session, Model model) {
        Property property = (Property) session.getAttribute("property");
        if(property == null) {
            return "redirect:/";
        }
        if(property.getAmenity() == null) {
            property.setAmenity(new Amenity());
        }
        model.addAttribute("editMode", false);
        model.addAttribute("amenity", property.getAmenity());

        return "amenities-details";
    }

    @PostMapping("/amenities")
    public String addAmenities(@ModelAttribute Amenity amenity, HttpSession session){
        Property property = (Property) session.getAttribute("property");
        if(property == null) {
            return "redirect:/";
        }

        property.setAmenity(amenity);

        session.setAttribute("property", property);

        return "redirect:/images";
    }

    @GetMapping("/images")
    public String propertyImages(Model model, HttpSession session) {
        model.addAttribute("editMode", session.getAttribute("property") != null);
        model.addAttribute("editMode", false);

        return "gallery";
    }

    @PostMapping("/images")
    public String uploadImages(@RequestParam("propertyImages") MultipartFile[] propertyImages,
                               HttpSession session) {
        Property property = (Property)session.getAttribute("property");
        if(property == null) {
            return "redirect:/";
        }
        propertyService.saveImage(property, propertyImages, session);

        session.removeAttribute("property");

        return "redirect:/";
    }

    // ---- Edit Property -----

    @GetMapping("/edit/{propertyId}")
    public String showEditForm(@PathVariable Long propertyId,
                               HttpSession session,
                               Model model) {
        Property property = propertyService.getPropertyById(propertyId);
        model.addAttribute("property", property);
        model.addAttribute("editMode", true);

        session.setAttribute("property", property);

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

        session.setAttribute("property", property);

        return "redirect:/edit/address";
    }

    @GetMapping("/edit/address")
    public String showEditAddressForm(HttpSession session, Model model) {
        Property property = (Property)session.getAttribute("property");
        if (property == null || property.getAddress() == null) {
            return "redirect:/";
        }

        model.addAttribute("address", property.getAddress());
        model.addAttribute("editMode", true);

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
    public String showRentalEditForm(HttpSession session, Model model) {
        Property property = (Property) session.getAttribute("property");
        if(property == null) {
            return "redirect:/";
        }

        RentalDto rentalDto = new RentalDto();

        rentalDto.setIsSale(property.getIsSale());
        rentalDto.setAvailableFor(property.getAvailableFor());
        rentalDto.setAvailableFrom(property.getAvailableFrom());
        rentalDto.setExpectedRent(property.getExpectedRent() != null ?
                property.getExpectedRent() : 0L);
        rentalDto.setExpectedDeposit(property.getExpectedDeposit() != null ?
                property.getExpectedDeposit() : 0l);
        rentalDto.setNegotiation(property.getNegotiation());
        rentalDto.setMonthlyMaintenance(property.getMonthlyMaintenance());
        rentalDto.setPreferredTenets(property.getPreferredTenets());
        rentalDto.setFurnishing(property.getFurnishing());
        rentalDto.setParking(property.getParking());
        rentalDto.setDescription(property.getDescription());
        rentalDto.setPrice(property.getPrice());
        rentalDto.setPropertyStatus(property.getPropertyStatus());

        model.addAttribute("rentalDto", rentalDto);
        model.addAttribute("editMode", true);

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
        property.setPropertyStatus(rentalDto.getPropertyStatus());
        property.setPrice(rentalDto.getPrice());

        session.setAttribute("property", property);

        return "redirect:/edit/amenities";
    }

    @GetMapping("/edit/amenities")
    public String showEditAmenityForm(HttpSession session, Model model) {
        Property property = (Property)session.getAttribute("property");
        if(property == null) {
            return "redirect:/";
        }
        if(property.getAmenity() == null) {
            property.setAmenity(new Amenity());
        }

        model.addAttribute("amenity", property.getAmenity());
        model.addAttribute("editMode", true);

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
    public String showGalleryEditForm(HttpSession session, Model model) {
        Property property = (Property) session.getAttribute("property");
        if(property == null) {
            return "redirect:/";
        }

        model.addAttribute("property", property);
        model.addAttribute("editMode", true);

        return "gallery";
    }

    @PostMapping("/edit/gallery")
    public String updateGallery(@RequestParam("propertyImages") MultipartFile[] propertyImages,
                                HttpSession session) {
        Property property = (Property) session.getAttribute("property");
        if(property == null) {
            return "redirect:/";
        }
        propertyService.saveImage(property, propertyImages, session);

        session.removeAttribute("property");

        return "redirect:/";
    }

    @GetMapping("/landingPage")
    public String getLandingPage(Model model) {
        return "landing-page";
    }

//    ---------------------Get All Properties---------------------------------

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
            @RequestParam(required = false) Integer propertyAge,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size,
            Model model,
            HttpSession session) {
        boolean isSale = "buy".equalsIgnoreCase(isSaleStr);

        User user = (User)session.getAttribute("user");

        Page<Property> propertyPage = propertyService.getAllProperties(
                isSale, city, searchKeyword, bhkType, propertyStatus, furnishing,
                propertyType, parking, propertyAge, sortBy, page, size);

        List<Long> bookmarkedIds = user.getBookmarkedProperties().stream()
                .map(Property::getPropertyId)
                .collect(Collectors.toList());

        model.addAttribute("bookmarkedProperties", bookmarkedIds);

        model.addAttribute("allProperties", propertyPage.getContent());
        model.addAttribute("currentPage", propertyPage.getNumber());
        model.addAttribute("totalPages", propertyPage.getTotalPages());
        model.addAttribute("totalItems", propertyPage.getTotalElements());
        model.addAttribute("pageSize", size);
        model.addAttribute("isSale", isSaleStr);
        model.addAttribute("searchKeyword", searchKeyword);
        model.addAttribute("bhkType", bhkType);
        model.addAttribute("city", city);
        model.addAttribute("furnishing", furnishing);
        model.addAttribute("parking", parking);
        model.addAttribute("propertyType", propertyType);
        model.addAttribute("propertyAge", propertyAge);
        model.addAttribute("propertyStatus", propertyStatus);
        model.addAttribute("sortBy", sortBy);

        return "all-properties";
    }

    @GetMapping("/loadMorePosts")
    public String loadMorePosts(
            @RequestParam("type") String isSaleStr,
            @RequestParam("city") String city,
            @RequestParam("query") String searchKeyword,
            @RequestParam(required = false) List<Integer> bhkType,
            @RequestParam(required = false) String propertyStatus,
            @RequestParam(required = false) List<String> furnishing,
            @RequestParam(required = false) List<String> propertyType,
            @RequestParam(required = false) List<String> parking,
            @RequestParam(required = false) Integer propertyAge,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size,
            Model model) {

        boolean isSale = "buy".equalsIgnoreCase(isSaleStr);

        Page<Property> propertyPage = propertyService.getAllProperties(
                isSale, city, searchKeyword, bhkType, propertyStatus, furnishing,
                propertyType, parking, propertyAge, sortBy, page, size);

        model.addAttribute("allProperties", propertyPage.getContent());
        model.addAttribute("hasNext", propertyPage.hasNext());

        return "fragments/postSection :: postSection";
    }


    @GetMapping("/viewProperty/{id}")
    public String viewProperty(@PathVariable Long id, Model model) {
        Property property = propertyService.getPropertyById(id);
        if (property == null || property.getAddress() == null) {
            return "error";
        }

        model.addAttribute("property", property);
        return "view-full-property";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "custom-login";
    }

    @GetMapping("/logout")
    public String logoutPage() {
        return "logout";
    }

    @GetMapping("/property/{propertyId}")
    public String deleteProperty(@PathVariable Long propertyId,
                                 RedirectAttributes redirectAttributes) {
        propertyService.deleteById(propertyId);

        redirectAttributes.addFlashAttribute("message", "Property deleted successfully !!!");

        return "redirect:/";
    }

//    ----------------------------------------------------------------------------------------------------------------

    @PostMapping("/toggleBookmark/{propertyId}")
    @ResponseBody
    public Map<String, Object> toggleBookmark(@PathVariable Long propertyId, HttpSession session) {
        User user = (User) session.getAttribute("user");

        boolean bookmarked = propertyService.saveBookMarks(propertyId, user);

        return Map.of(
                "success", true,
                "bookmarked", bookmarked
        );
    }

    @PostMapping("/removeBookMarks/{userId}/{propertyId}")
    public String removeBookMarka(@PathVariable("userId") Long userId, @PathVariable("propertyId") Long propertyId,HttpSession session) {

        propertyService.removeBookmarks(propertyId, userId);

        return "redirect:/shortlisted-properties/"+userId;
    }
}