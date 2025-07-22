package com.noBroker.nobroker_application_project.dto;

import lombok.Getter;
import lombok.Setter;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
public class RentalDto {
    private Boolean isSale;
    private long expectedRent;
    private long expectedDeposit;
    private String monthlyMaintenance;
    private Boolean negotiation;
    private Long price;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date availableFrom;

    private String availableFor;

    private String preferredTenets;
    private String furnishing;
    private String parking;
    private String description;
}
