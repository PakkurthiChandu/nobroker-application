package com.noBroker.nobroker_application_project.service.serviceimpl;

import com.noBroker.nobroker_application_project.model.Address;
import com.noBroker.nobroker_application_project.repository.AddressRepository;
import com.noBroker.nobroker_application_project.service.AddressService;

import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public Address findOrCreate(Address address) {
        Address existingAddress = addressRepository.findByCityAndLocalityAndLandmark(address.getCity(),
                address.getLocality(), address.getLandmark());

        if (existingAddress != null) {
            return existingAddress;
        } else {
            return addressRepository.save(address);
        }
    }

    @Override
    public void delete(Address address) {
        addressRepository.delete(address);
    }
}
