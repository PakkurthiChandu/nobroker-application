package com.noBroker.nobroker_application_project.service;

import com.noBroker.nobroker_application_project.dto.RentalDto;
import com.noBroker.nobroker_application_project.model.*;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.noBroker.nobroker_application_project.repository.PropertyRepository;

import com.noBroker.nobroker_application_project.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.print.Pageable;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class PropertyService {

    Property property;

    private final Cloudinary cloudinary;
    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;

    public PropertyService(Cloudinary cloudinary, PropertyRepository propertyRepository,
                           UserRepository userRepository) {
        this.cloudinary = cloudinary;
        this.propertyRepository = propertyRepository;
        this.userRepository = userRepository;
    }

    public Property findById(Long id) {
        return propertyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No id Found: " + id));
    }

    public Property getPropertyById(Long id) {
        return propertyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No property found with id: " + id));
    }
    public void saveProperty(Property property) {
        propertyRepository.save(property);
    }

    public void saveAddress(Property property, Address address) {

        property.setAddress(address);
        propertyRepository.save(property);
    }

    public void saveRentals(Property property, RentalDto rentalDto) {
        property.setIsSale(rentalDto.getIsSale());
        property.setExpectedRent(rentalDto.getExpectedRent());
        property.setExpectedDeposit(rentalDto.getExpectedDeposit());
        property.setMonthlyMaintenance(rentalDto.getMonthlyMaintenance());
        property.setNegotiation(rentalDto.getNegotiation());
        property.setAvailableFrom(rentalDto.getAvailableFrom());
        property.setPreferredTenets(rentalDto.getPreferredTenets());
        property.setFurnishing(rentalDto.getFurnishing());
        property.setParking(rentalDto.getParking());
        property.setDescription(rentalDto.getDescription());

        propertyRepository.save(property);
    }

    public void saveAmenities(Property property, Amenity amenity) {
        property.setAmenity(amenity);
        propertyRepository.save(property);
    }

    public void saveImage(Property dbProperty, MultipartFile[] propertyImages) {
        for (MultipartFile multipartFile : propertyImages) {
            if (multipartFile == null || multipartFile.isEmpty()) {
                continue;
            }

            try {
                Map<?, ?> uploadResult = cloudinary.uploader().upload(
                        multipartFile.getBytes(),
                        ObjectUtils.emptyMap()
                );

                String imageUrl = uploadResult.get("secure_url").toString();

                Image image = new Image();
                image.setImageUrl(imageUrl);
                image.setProperty(dbProperty);
                dbProperty.getPhotos().add(image);
            } catch (IOException e) {
                throw new RuntimeException("Image upload failed", e);
            }
        }

        propertyRepository.save(dbProperty);
    }

    public List<Property> getAllProperties(boolean isSale, String city, String keyword,
                                           List<Integer> bhkType,
                                           String propertyStatus,
                                           List<String> furnishing,
                                           List<String> propertyType,
                                           List<String> parking) {
        keyword = (keyword == null || keyword.trim().isEmpty()) ? "" : keyword;
        return propertyRepository.searchProperties(isSale, city, keyword, bhkType);
    }
}