package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.DTO.security.AuthenticationRequest;
import org.example.DTO.security.AuthenticationResponse;
import org.example.DTO.security.RegisterRequest;
import org.example.service.AuthenticationService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class AuthenticationController {

    private final AuthenticationService authService;

    @MutationMapping
    public Boolean registerUser(
            @Argument RegisterRequest request){
         return authService.register(request);
    }

    @QueryMapping
    public AuthenticationResponse authenticateUser(
            @Argument AuthenticationRequest request){

        return authService.authenticate(request);
    }
}
