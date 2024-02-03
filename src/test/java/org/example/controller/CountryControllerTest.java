package org.example.controller;

import org.example.entity.Country;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.GraphQlTester;

@AutoConfigureGraphQlTester
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class CountryControllerTest {
    @Autowired
    private GraphQlTester graphQlTester;
    @Test
    void shouldAddCategory(){
        graphQlTester.documentName("country-add")
                .variable("countryName", "Ukraine")
                .execute()
                .path("addCountry")
                .entity(Country.class)
                .satisfies(country -> {
                    Assertions.assertEquals("Ukraine", country.getName());
                });
    }
}
