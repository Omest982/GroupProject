package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.DTO.NewAddress;
import org.example.entity.Address;
import org.example.entity.User;
import org.example.exception.UserNotFoundException;
import org.example.repository.AddressRepository;
import org.example.service.AddressService;
import org.example.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
    private final UserService userService;
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
    public Address addUserAddress(Long userId, NewAddress address) {
        User user = userService.getUserById(userId);

        if (user == null){
            throw new UserNotFoundException("User with id " +
                    userId + " not found!");
        }

        Address userAddress = addOrGetAddress(address);

        user.getAddresses().add(userAddress);

        userService.saveUser(user);

        return userAddress;
    }

    @Transactional
    @Override
    public Address addOrGetAddress(NewAddress newAddress) {
        Address transientAddress = Address.builder()
                .region(newAddress.getRegion())
                .city(newAddress.getCity())
                .street(newAddress.getStreet())
                .house(newAddress.getHouse())
                .build();

        Address persistantAddress = getAddressByAllFields(transientAddress);

        if (persistantAddress == null){
            return addressRepository.save(transientAddress);
        }

        return persistantAddress;
    }
}
