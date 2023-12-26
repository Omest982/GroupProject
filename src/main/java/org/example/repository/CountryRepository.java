package org.example.repository;

import org.example.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CountryRepository extends JpaRepository<Country, Long> {
    Country findByName(String countryName);
    List<Country> findByNameIn(List<String> countryNames);
}
