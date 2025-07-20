package com.noBroker.nobroker_application_project.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "Photos")
@Getter
@Setter
@ToString
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long photoId;

    private String imageUrl;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "property_id")
    private Property property;
}