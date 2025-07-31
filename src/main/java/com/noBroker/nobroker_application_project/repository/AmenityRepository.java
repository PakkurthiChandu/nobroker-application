package com.noBroker.nobroker_application_project.repository;

import com.noBroker.nobroker_application_project.model.Amenity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AmenityRepository extends JpaRepository<Amenity, Long> {

    @Query("SELECT a FROM Amenity a WHERE " +
            "a.bathrooms = :bathrooms AND " +
            "(:balcony IS NULL OR a.balcony = :balcony) AND " +
            "(:waterSupply IS NULL OR a.waterSupply = :waterSupply) AND " +
            "(:petAllowed IS NULL OR a.petAllowed = :petAllowed) AND " +
            "(:gym IS NULL OR a.gym = :gym) AND " +
            "(:nonVeg IS NULL OR a.nonVeg = :nonVeg) AND " +
            "(:gatedSecurity IS NULL OR a.gatedSecurity = :gatedSecurity) AND " +
            "(:showProperty IS NULL OR a.showProperty = :showProperty) AND " +
            "(:propertyCondition IS NULL OR a.propertyCondition = :propertyCondition) AND " +
            "(:secondaryNumber IS NULL OR a.secondaryNumber = :secondaryNumber) AND " +
            "(:nearByPlace IS NULL OR a.nearByPlace = :nearByPlace) AND " +
            "(:lift IS NULL OR a.lift = :lift) AND " +
            "(:gasPipeLine IS NULL OR a.gasPipeLine = :gasPipeLine) AND " +
            "(:airConditioner IS NULL OR a.airConditioner = :airConditioner) AND " +
            "(:park IS NULL OR a.park = :park) AND " +
            "(:houseKeeping IS NULL OR a.houseKeeping = :houseKeeping) AND " +
            "(:internetService IS NULL OR a.internetService = :internetService) AND " +
            "(:powerBackUp IS NULL OR a.powerBackUp = :powerBackUp) AND " +
            "(:serventRoom IS NULL OR a.serventRoom = :serventRoom) AND " +
            "(:swimmingPool IS NULL OR a.swimmingPool = :swimmingPool) AND " +
            "(:fireSafety IS NULL OR a.fireSafety = :fireSafety)")
    Amenity findMatchingAmenity(
            @Param("bathrooms") int bathrooms,
            @Param("balcony") Integer balcony,
            @Param("waterSupply") String waterSupply,
            @Param("petAllowed") Boolean petAllowed,
            @Param("gym") Boolean gym,
            @Param("nonVeg") Boolean nonVeg,
            @Param("gatedSecurity") Boolean gatedSecurity,
            @Param("showProperty") String showProperty,
            @Param("propertyCondition") String propertyCondition,
            @Param("secondaryNumber") String secondaryNumber,
            @Param("nearByPlace") String nearByPlace,
            @Param("lift") Boolean lift,
            @Param("gasPipeLine") Boolean gasPipeLine,
            @Param("airConditioner") Boolean airConditioner,
            @Param("park") Boolean park,
            @Param("houseKeeping") Boolean houseKeeping,
            @Param("internetService") Boolean internetService,
            @Param("powerBackUp") Boolean powerBackUp,
            @Param("serventRoom") Boolean serventRoom,
            @Param("swimmingPool") Boolean swimmingPool,
            @Param("fireSafety") Boolean fireSafety
    );
}