package org.example.repository;

import org.example.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface CountryRepository extends JpaRepository<Country, Long> {
    Country findByName(String countryName);
    List<Country> findAllByNameIn(Set<String> countryNames);
}
