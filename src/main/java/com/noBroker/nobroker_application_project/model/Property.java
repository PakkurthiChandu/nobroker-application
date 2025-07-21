package com.noBroker.nobroker_application_project.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
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
    private Long expectedRent;
    private Long expectedDeposit;
    private String monthlyMaintenance;
    private String preferredTenets;

    @Column(columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean negotiation;

    @Temporal(TemporalType.DATE)
    private Date availableFrom;

    private String furnishing;
    private String parking;
    private String propertyStatus;
    private Long price;

    @Column(columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean isSale = true;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToMany(mappedBy = "bookmarkedProperties", cascade = CascadeType.ALL)
    private Set<User> bookmarkedByUsers = new HashSet<>();

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Image> photos = new HashSet<>();

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "amenity_id")
    private Amenity amenity;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;
}