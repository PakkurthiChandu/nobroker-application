package com.noBroker.nobroker_application_project.service.serviceimpl;

import com.noBroker.nobroker_application_project.model.Amenity;
import com.noBroker.nobroker_application_project.repository.AmenityRepository;
import com.noBroker.nobroker_application_project.service.AmenityService;

import org.springframework.stereotype.Service;

@Service
public class AmenityServiceImpl implements AmenityService {
    private final AmenityRepository amenityRepository;

    public AmenityServiceImpl(AmenityRepository amenityRepository) {
        this.amenityRepository = amenityRepository;
    }

    @Override
    public Amenity findOrCreateAmenity(Amenity amenity) {
        Amenity existing = amenityRepository.findMatchingAmenity(
                amenity.getBathrooms(),
                amenity.getBalcony(),
                amenity.getWaterSupply(),
                amenity.getPetAllowed(),
                amenity.getGym(),
                amenity.getNonVeg(),
                amenity.getGatedSecurity(),
                amenity.getShowProperty(),
                amenity.getPropertyCondition(),
                amenity.getSecondaryNumber(),
                amenity.getNearByPlace(),
                amenity.getLift(),
                amenity.getGasPipeLine(),
                amenity.getAirConditioner(),
                amenity.getPark(),
                amenity.getHouseKeeping(),
                amenity.getInternetService(),
                amenity.getPowerBackUp(),
                amenity.getServentRoom(),
                amenity.getSwimmingPool(),
                amenity.getFireSafety()
        );

        if (existing != null) {

            return existing;
        } else {

            return amenityRepository.save(amenity);
        }
    }

    @Override
    public void delete(Amenity amenity) {
        amenityRepository.delete(amenity);
    }
}