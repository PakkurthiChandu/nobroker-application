package com.noBroker.nobroker_application_project.service;

import com.noBroker.nobroker_application_project.model.*;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.noBroker.nobroker_application_project.repository.PropertyRepository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

import java.util.List;
import java.util.Map;

@Service
public class PropertyService {

    private final Cloudinary cloudinary;
    private final PropertyRepository propertyRepository;

    public PropertyService(Cloudinary cloudinary, PropertyRepository propertyRepository) {
        this.cloudinary = cloudinary;
        this.propertyRepository = propertyRepository;
    }

    public Property getPropertyById(Long id) {
        return propertyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No property found with id: " + id));
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
                                           List<String> parking,
                                           Integer propertyAge,
                                           String sortBy) {
        keyword = (keyword == null || keyword.trim().isEmpty()) ? "" : keyword;

        Sort sort =  Sort.by(Sort.Direction.DESC, "createdAt");

        if(sortBy != null) {
            switch (sortBy) {
                case "oldest": sort = Sort.by(Sort.Direction.ASC, "createdAt");
                    break;
                case "priceHighLow": sort = Sort.by(Sort.Direction.DESC, "price");
                    break;
                case "priceLowHigh": sort = Sort.by(Sort.Direction.ASC, "price");
                    break;
            }
        }

        Pageable pageable = PageRequest.of(0, 10, sort);

        return propertyRepository.searchProperties(isSale, city, keyword.toLowerCase(), bhkType, furnishing, parking, propertyType,
                propertyAge, propertyStatus, pageable);
    }
}