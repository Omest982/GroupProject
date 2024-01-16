package org.example.service;

import org.example.DTO.NewAddress;
import org.example.entity.Address;

public interface AddressService{
    Address getAddressByAllFields(Address address);

    Address addUserAddress(Long userId, NewAddress address);
    Address addOrGetAddress(NewAddress newAddress);
}
