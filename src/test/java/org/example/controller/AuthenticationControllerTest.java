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
    void registerUser(){
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
                .entity(Boolean.class)
                .satisfies(Assertions::assertTrue);
    }

    @Test
    @Order(2)
    void failRegistrationEmailDuplicate(){
        Map<String, Object> newRegisterRequest = new HashMap<>();
        newRegisterRequest.put("firstName", "Ivan");
        newRegisterRequest.put("lastName", "Poberezhets");
        newRegisterRequest.put("password", "123456");
        newRegisterRequest.put("email", "omest982@gmail.com");
        newRegisterRequest.put("phoneNumber", "654321");
        newRegisterRequest.put("birthdayDate", "16.02.2004");

        graphQlTester.documentName("user_register")
                .variable("request", newRegisterRequest)
                .execute()
                .errors()
                .satisfy(exception ->{
                    Assertions.assertEquals(
                            "User with this email or phone number already exists!",
                            exception.get(0).getMessage());
                });
    }

    @Test
    @Order(3)
    void failRegistrationPhoneDuplicate() {
        Map<String, Object> newRegisterRequest = new HashMap<>();
        newRegisterRequest.put("firstName", "Ivan");
        newRegisterRequest.put("lastName", "Poberezhets");
        newRegisterRequest.put("password", "123456");
        newRegisterRequest.put("email", "vasya.pupkin@gmail.com");
        newRegisterRequest.put("phoneNumber", "123456");
        newRegisterRequest.put("birthdayDate", "16.02.2004");

        graphQlTester.documentName("user_register")
                .variable("request", newRegisterRequest)
                .execute()
                .errors()
                .satisfy(exception ->{
                    Assertions.assertEquals(
                            "User with this email or phone number already exists!",
                            exception.get(0).getMessage());
                });
    }
}
