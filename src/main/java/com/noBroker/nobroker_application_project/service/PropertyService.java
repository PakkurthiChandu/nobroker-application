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

    Property property;

    private final Cloudinary cloudinary;
    private final PropertyRepository propertyRepository;

    public PropertyService(Cloudinary cloudinary, PropertyRepository propertyRepository) {
        this.cloudinary = cloudinary;
        this.propertyRepository = propertyRepository;
    }

    public void saveProperty(Property property) {
        this.property = property;
    }

    public void saveAddress(Address address) {

        property.setAddress(address);
    }

    public void saveRentails(RentalDto rentalDto) {

        property.setIsSale(rentalDto.getIsSale());
        property.setExpectedRent(rentalDto.getExpectedRent());
        property.setExceptedDeposit(rentalDto.getExpectedDeposite());
        property.setMontlyMaintenance(rentalDto.getMontlyMaintenance());
        property.setNegotiation(rentalDto.getNegotiation());
        property.setAvailableFrom(rentalDto.getAvailableFrom());
        property.setPreferredTenets(rentalDto.getPreferredTenets());
        property.setFurnishing(rentalDto.getFurnishing());
        property.setParking(rentalDto.getParking());
        property.setDescription(rentalDto.getDescription());


    }

    public void saveAmenities(Amenity amenity) {
        property.setAmenity(amenity);
    }

    public void saveImage(MultipartFile[] propertyImages) {
        for (MultipartFile multipartFile : propertyImages) {
            if (multipartFile== null || multipartFile.isEmpty()) {
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
                image.setProperty(property);

                property.getPhotos().add(image);
            } catch (IOException e) {
                throw new RuntimeException("Image upload failed", e);
            }
        }

        propertyRepository.save(property);
    }

    public Property getPropertyById(long id) {
        return propertyRepository.findByPropertyId(id);
    }

}