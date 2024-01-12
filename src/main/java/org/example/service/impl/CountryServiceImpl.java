package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.entity.Country;
import org.example.repository.CountryRepository;
import org.example.service.CountryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {
    private final CountryRepository countryRepository;

    @Override
    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }

    @Override
    public Country getCountryById(Long id) {
        return countryRepository.findById(id).orElse(null);
    }

    @Override
    public List<Country> getAllCountriesByIds(List<Long> countriesMadeInIds) {
        return countryRepository.findAllById(countriesMadeInIds);
    }

    @Override
    public Country addCountry(String countryName) {
        Country country = Country.builder()
                .name(countryName)
                .build();
        return countryRepository.save(country);
    }

    @Override
    public String deleteCountry(Long countryId) {
        countryRepository.deleteById(countryId);
        if (getCountryById(countryId) == null){
            return "Successfully deleted country!";
        }
        return "Failed to delete country!";
    }
}
