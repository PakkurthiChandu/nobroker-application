package com.noBroker.nobroker_application_project.service;

import com.noBroker.nobroker_application_project.dto.RentalDto;
import com.noBroker.nobroker_application_project.model.Address;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.noBroker.nobroker_application_project.model.Image;
import com.noBroker.nobroker_application_project.model.Property;
import com.noBroker.nobroker_application_project.repository.PropertyRepository;
import com.noBroker.nobroker_application_project.model.Amenity;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class PropertyService {

    private final Cloudinary cloudinary;
    private final PropertyRepository propertyRepository;

    public PropertyService(Cloudinary cloudinary, PropertyRepository propertyRepository) {
        this.cloudinary = cloudinary;
        this.propertyRepository = propertyRepository;
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
}