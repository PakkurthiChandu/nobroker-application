package com.noBroker.nobroker_application_project.model;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "address", indexes = {
        @Index(name = "idx_address_city", columnList = "city"),
        @Index(name = "idx_address_locality", columnList = "locality"),
        @Index(name = "idx_address_landmark", columnList = "landmark")
})
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

    private String city;
    private String locality;
    private String landmark;
    private Double latitude;
    private Double longitude;

    @OneToMany(mappedBy = "address")
    private Set<Property> properties = new HashSet<>();
}