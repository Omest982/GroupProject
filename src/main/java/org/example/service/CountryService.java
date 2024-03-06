package org.example.service;

import org.example.entity.Country;

import java.util.List;
import java.util.Set;

public interface CountryService {

    List<Country> getAllCountries();
    Country getCountryById(Long id);

    List<Country> getAllCountriesByIds(Iterable<Long> countriesMadeInIds);

    Country addCountry(String countryName);

    Boolean deleteCountry(Long countryId);
}
