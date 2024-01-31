package org.example.controller;

import org.example.entity.Brand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.GraphQlTester;

@AutoConfigureGraphQlTester
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class BrandControllerTest {
    @Autowired
    private GraphQlTester graphQlTester;
    @Test
    void shouldAddAndReturnBrand(){
        Brand brandTest = graphQlTester.documentName("brand-mutation")
                .variable("brandName", "Loui")
                .execute()
                .path("addBrand")
                .entity(Brand.class)
                .satisfies(brand -> {
                    Assertions.assertEquals("Loui", brand.getName());
                })
                .get();
    }
}
