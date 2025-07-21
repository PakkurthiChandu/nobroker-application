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

    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean petAllowed;
    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean gym;
    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean nonVeg;
    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean gatedSecurity;

    private String showProperty;
    private String propertyCondition;
    private String secondaryNumber;
    private String nearByPlace;

    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean lift;
    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean gasPipeLine;
    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean airConditioner;
    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean park;
    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean houseKeeping;
    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean internetService;
    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean powerBackUp;
    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean serventRoom;
    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean swimmingPool;
    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean fireSafety;

    @OneToMany(mappedBy = "amenity")
    private Set<Property> properties = new HashSet<>();
}