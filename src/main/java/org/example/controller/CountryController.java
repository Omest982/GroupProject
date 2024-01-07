package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.entity.Country;
import org.example.service.CountryService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CountryController {
    private final CountryService countryService;

    @QueryMapping
    public List<Country> getAllCountries(){
        return countryService.getAllCountries();
    }

    @MutationMapping
    public Country addCountry(@Argument String countryName){
        return countryService.addCountry(countryName);
    }
}
