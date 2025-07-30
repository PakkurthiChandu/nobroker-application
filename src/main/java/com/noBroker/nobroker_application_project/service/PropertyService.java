package com.noBroker.nobroker_application_project.service;

import com.noBroker.nobroker_application_project.model.*;
import com.noBroker.nobroker_application_project.dto.RentalDto;

import jakarta.servlet.http.HttpSession;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

@Service
public interface PropertyService {

    Property getPropertyById(Long id);

    void saveImage(Property property, MultipartFile[] propertyImages, HttpSession session);

    Page<Property> getAllProperties(boolean isSale, String city, String keyword, List<Integer> bhkType,
                                           String propertyStatus, List<String> furnishing, List<String> propertyType,
                                           List<String> parking, Integer propertyAge, Double minBuiltUpArea,
                                           Double maxBuiltUpArea, Long minRent, Long maxRent, String sortBy,
                                           int page, int size);

    Set<Property> getBookmarkedPropertyDTOs(Long userId);

    void deleteById(Long propertyId);

    RentalDto getRentalDetails(Property property);

    Property updatePropertyDetails(Property property,Property updatedProperty);

    Property saveUpdatedRentalDetails(Property property, RentalDto rentalDto);

    Property saveRentalDetails(Property property, RentalDto rentalDto);
}