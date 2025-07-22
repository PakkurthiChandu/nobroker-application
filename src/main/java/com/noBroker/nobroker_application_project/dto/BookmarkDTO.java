package com.noBroker.nobroker_application_project.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookmarkDTO {
    private Long propertyId;
    private String action; // "add" or "remove"
}