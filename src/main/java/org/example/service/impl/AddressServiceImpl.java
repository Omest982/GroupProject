package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.entity.Address;
import org.example.repository.AddressRepository;
import org.example.service.AddressService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
    @Override
    public Address getAddressByAllFields(Address address) {
        return addressRepository.findByRegionAndCityAndStreetAndHouse(
                address.getRegion(),
                address.getCity(),
                address.getStreet(),
                address.getHouse()
        );
    }

    @Transactional
    @Override
    public Address saveAddress(Address transientAddress) {
        return addressRepository.save(transientAddress);
    }
}
