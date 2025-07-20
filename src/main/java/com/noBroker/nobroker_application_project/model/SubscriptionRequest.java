package com.noBroker.nobroker_application_project.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class SubscriptionRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long requestId;

    private Long userId;
}
