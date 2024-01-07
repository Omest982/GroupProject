package org.example.service;

import org.example.entity.Country;

import java.util.List;

public interface CountryService {

    List<Country> getAllCountries();
    Country getCountryById(Long id);

    List<Country> getAllCountriesByIds(List<Long> countriesMadeInIds);

    Country addCountry(String countryName);
}
