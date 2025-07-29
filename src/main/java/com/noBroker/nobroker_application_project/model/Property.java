package com.noBroker.nobroker_application_project.model;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "property", indexes = {
        @Index(name = "idx_property_issale", columnList = "isSale"),
        @Index(name = "idx_property_expected_rent", columnList = "expectedRent"),
        @Index(name = "idx_property_bhk", columnList = "bhkType"),
        @Index(name = "idx_property_area", columnList = "builtUpArea"),
        @Index(name = "idx_property_created", columnList = "createdAt"),
        @Index(name = "idx_property_furnishing", columnList = "furnishing"),
        @Index(name = "idx_property_parking", columnList = "parking"),
        @Index(name = "idx_property_age", columnList = "propertyAge"),
        @Index(name = "idx_property_status", columnList = "propertyStatus"),
        @Index(name = "idx_property_address", columnList = "address_id"),
        @Index(name = "idx_property_amenity", columnList = "amenity_id"),
        @Index(name = "idx_property_owner", columnList = "user_id")
})
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long propertyId;

    private String apartmentType;
    private String apartmentName;
    private Long bhkType;
    private Long floor;
    private Long totalFloors;
    private Long propertyAge;
    private String facing;
    private Double builtUpArea;
    private String availableFor;
    private Long expectedRent = 0L;
    private Long expectedDeposit = 0L;
    private String monthlyMaintenance;
    private String preferredTenets;
    private Boolean negotiation = false;

    @Temporal(TemporalType.DATE)
    private Date availableFrom;

    private String furnishing;
    private String parking;
    private String propertyStatus;
    private Long price;
    private Boolean isSale = true;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToMany(mappedBy = "bookmarkedProperties", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<User> bookmarkedByUsers = new HashSet<>();

    @OneToMany(mappedBy = "property", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},
            fetch = FetchType.EAGER)
    private Set<Image> photos = new HashSet<>();

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "amenity_id")
    private Amenity amenity;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "address_id")
    private Address address;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;
}