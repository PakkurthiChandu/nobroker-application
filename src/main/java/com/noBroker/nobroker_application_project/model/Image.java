package com.noBroker.nobroker_application_project.model;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "Photos", indexes = {
        @Index(name = "idx_image_property", columnList = "property_id")
})
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long photoId;

    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;
}