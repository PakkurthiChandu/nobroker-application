package com.noBroker.nobroker_application_project.repository;

import com.noBroker.nobroker_application_project.model.Address;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address,Long> {
    Address findByCityAndLocalityAndLandmark(String city, String locality, String landmark);
}