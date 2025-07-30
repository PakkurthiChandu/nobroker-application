package com.noBroker.nobroker_application_project.service.serviceimpl;

import com.noBroker.nobroker_application_project.model.*;
import com.noBroker.nobroker_application_project.dto.RentalDto;
import com.noBroker.nobroker_application_project.repository.UserRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.noBroker.nobroker_application_project.repository.PropertyRepository;
import com.noBroker.nobroker_application_project.service.AddressService;
import com.noBroker.nobroker_application_project.service.AmenityService;
import com.noBroker.nobroker_application_project.service.PropertyService;

import jakarta.servlet.http.HttpSession;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class PropertyServiceImpl implements PropertyService {

    private final Cloudinary cloudinary;
    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;
    private final AddressService addressService;
    private final AmenityService amenityService;

    public PropertyServiceImpl(Cloudinary cloudinary, PropertyRepository propertyRepository,
                               UserRepository userRepository, AddressService addressService,
                               AmenityService amenityService) {
        this.cloudinary = cloudinary;
        this.propertyRepository = propertyRepository;
        this.userRepository = userRepository;
        this.addressService = addressService;
        this.amenityService = amenityService;
    }

    public Property getPropertyById(Long id) {
        return propertyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No property found with id: " + id));
    }

    public void saveImage(Property property, MultipartFile[] propertyImages, HttpSession session) {
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
                image.setProperty(property);

                property.getPhotos().add(image);
            } catch (IOException e) {
                throw new RuntimeException("Image upload failed", e);
            }
        }

        property.setOwner((User) session.getAttribute("user"));

        propertyRepository.save(property);
    }

    public Page<Property> getAllProperties(boolean isSale, String city, String keyword, List<Integer> bhkType,
                                           String propertyStatus, List<String> furnishing, List<String> propertyType,
                                           List<String> parking, Integer propertyAge, Double minBuiltUpArea,
                                           Double maxBuiltUpArea, Long minRent, Long maxRent, String sortBy,
                                           int page, int size) {

        keyword = (keyword == null || keyword.trim().isEmpty()) ? "" : keyword;

        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");

        if (sortBy != null) {
            switch (sortBy) {
                case "oldest":
                    sort = Sort.by(Sort.Direction.ASC, "createdAt");
                    break;
                case "priceHighLow":
                    String priceField = isSale ? "price" : "expectedRent";
                    sort = Sort.by(Sort.Direction.DESC, priceField);
                    break;
                case "priceLowHigh":
                    String priceFieldAsc = isSale ? "price" : "expectedRent";
                    sort = Sort.by(Sort.Direction.ASC, priceFieldAsc);
                    break;
            }
        }

        Pageable pageable = PageRequest.of(page, size, sort);

        return propertyRepository.searchProperties(
                isSale, city, keyword.toLowerCase().split(",")[0].trim(), bhkType, furnishing, parking,
                propertyType, propertyAge, propertyStatus, minBuiltUpArea, maxBuiltUpArea,
                minRent, maxRent, pageable);
    }

    public Set<Property> getBookmarkedPropertyDTOs(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return user.getBookmarkedProperties();
    }

    public void deleteById(Long propertyId) {
        Property property = propertyRepository.findById(propertyId).orElse(null);

        if (property != null) {
            propertyRepository.delete(property);

            if (property.getAddress().getProperties().size() <= 1) {
                addressService.delete(property.getAddress());
            }

            if (property.getAmenity().getProperties().size() <= 1) {
                amenityService.delete(property.getAmenity());
            }
        }
    }

    public RentalDto getRentalDetails(Property property) {
        return getRentalDto(property);
    }

    private RentalDto getRentalDto(Property property) {
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
        rentalDto.setPrice(property.getPrice());
        rentalDto.setPropertyStatus(property.getPropertyStatus());

        return rentalDto;
    }

    public Property updatePropertyDetails(Property property,Property updatedProperty) {
        property.setApartmentType(updatedProperty.getApartmentType());
        property.setApartmentName(updatedProperty.getApartmentName());
        property.setBhkType(updatedProperty.getBhkType());
        property.setFloor(updatedProperty.getFloor());
        property.setTotalFloors(updatedProperty.getTotalFloors());
        property.setPropertyAge(updatedProperty.getPropertyAge());
        property.setFacing(updatedProperty.getFacing());
        property.setBuiltUpArea(updatedProperty.getBuiltUpArea());

        return property;
    }

    public Property saveUpdatedRentalDetails(Property property, RentalDto rentalDto) {
        property.setIsSale(rentalDto.getIsSale());
        property.setAvailableFor(rentalDto.getAvailableFor());
        property.setExpectedRent(rentalDto.getExpectedRent() != null?
                rentalDto.getExpectedRent() : 0L);
        property.setExpectedDeposit(rentalDto.getExpectedDeposit());
        property.setNegotiation(rentalDto.getNegotiation());
        property.setMonthlyMaintenance(rentalDto.getMonthlyMaintenance());
        property.setAvailableFrom(rentalDto.getAvailableFrom());
        property.setPreferredTenets(rentalDto.getPreferredTenets());
        property.setFurnishing(rentalDto.getFurnishing());
        property.setParking(rentalDto.getParking());
        property.setDescription(rentalDto.getDescription());
        property.setPropertyStatus(rentalDto.getPropertyStatus());
        property.setPrice(rentalDto.getPrice() != null?
                rentalDto.getPrice() : 0L);

        return property;
    }

    public Property saveRentalDetails(Property property, RentalDto rentalDto) {
        property.setIsSale(rentalDto.getIsSale());
        property.setAvailableFor(rentalDto.getAvailableFor());
        property.setAvailableFrom(rentalDto.getAvailableFrom());
        property.setExpectedRent(rentalDto.getExpectedRent() != null?
                rentalDto.getExpectedRent() : 0L);
        property.setExpectedDeposit(rentalDto.getExpectedDeposit());
        property.setNegotiation(rentalDto.getNegotiation());
        property.setMonthlyMaintenance(rentalDto.getMonthlyMaintenance());
        property.setPreferredTenets(rentalDto.getPreferredTenets());
        property.setFurnishing(rentalDto.getFurnishing());
        property.setParking(rentalDto.getParking());
        property.setDescription(rentalDto.getDescription());
        property.setPrice(rentalDto.getPrice() != null?
                rentalDto.getPrice() : 0L);
        property.setPropertyStatus(rentalDto.getPropertyStatus());

        return property;
    }
}