package org.example.repository;

import org.example.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
    Address findByRegionAndCityAndStreetAndHouse(
            String region,
            String city,
            String street,
            String house
            );
}
