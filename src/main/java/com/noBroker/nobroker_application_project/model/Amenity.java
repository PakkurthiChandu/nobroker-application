package com.noBroker.nobroker_application_project.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Amenity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long amenityId;

    private int bathrooms;
    private Integer balcony;
    private String waterSupply;

    private Boolean petAllowed;
    private Boolean gym;
    private Boolean nonVeg;
    private Boolean gatedSecurity;

    private String showProperty;
    private String propertyCondition;
    private String secondaryNumber;
    private String nearByPlace;

    private Boolean lift;
    private Boolean gasPipeLine;
    private Boolean airConditioner;
    private Boolean park;
    private Boolean houseKeeping;
    private Boolean internetService;
    private Boolean powerBackUp;
    private Boolean serventRoom;
    private Boolean swimmingPool;
    private Boolean fireSafety;

    @OneToMany(mappedBy = "amenity")
    private Set<Property> properties = new HashSet<>();
}