package org.example.controller;

import org.example.DTO.security.AuthenticationResponse;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.GraphQlTester;

import java.util.HashMap;
import java.util.Map;

@AutoConfigureGraphQlTester
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class AuthenticationControllerTest {
    @Autowired
    private GraphQlTester graphQlTester;

    @Test
    @Order(1)
    void shouldRegisterUser(){
        Map<String, Object> newRegisterRequest = new HashMap<>();
        newRegisterRequest.put("firstName", "Ivan");
        newRegisterRequest.put("lastName", "Poberezhets");
        newRegisterRequest.put("password", "123456");
        newRegisterRequest.put("email", "omest982@gmail.com");
        newRegisterRequest.put("phoneNumber", "123456");
        newRegisterRequest.put("birthdayDate", "16.02.2004");

        graphQlTester.documentName("user_register")
                .variable("request", newRegisterRequest)
                .execute()
                .path("registerUser")
                .entity(AuthenticationResponse.class)
                .satisfies(response -> {
                    Assertions.assertNotNull(response.getJwtToken());
                });
    }

    @Test
    @Order(2)
    void shouldFailRegistration(){
        Map<String, Object> newRegisterRequest = new HashMap<>();
        newRegisterRequest.put("firstName", "Ivan");
        newRegisterRequest.put("lastName", "Poberezhets");
        newRegisterRequest.put("password", "123456");
        newRegisterRequest.put("email", "omest982@gmail.com");
        newRegisterRequest.put("phoneNumber", "123456");
        newRegisterRequest.put("birthdayDate", "16.02.2004");

        graphQlTester.documentName("user_register")
                .variable("request", newRegisterRequest)
                .execute()
                .errors();
    }
}
