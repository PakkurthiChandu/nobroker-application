package com.noBroker.nobroker_application_project.service;

import com.noBroker.nobroker_application_project.model.Address;

public interface AddressService {

    Address findOrCreate(Address address);

    void delete(Address address);
}