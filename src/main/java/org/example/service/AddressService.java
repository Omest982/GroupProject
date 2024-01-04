package org.example.service;

import org.example.entity.Address;

public interface AddressService{
    Address getAddressByAllFields(Address address);

    Address saveAddress(Address transientAddress);
}
