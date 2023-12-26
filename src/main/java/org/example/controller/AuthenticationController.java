package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.security.DTO.AuthenticationRequest;
import org.example.security.DTO.AuthenticationResponse;
import org.example.security.DTO.RegisterRequest;
import org.example.service.AuthenticationService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@RequiredArgsConstructor
@CrossOrigin(origins = {"http://13.51.170.112:8080", "http://localhost:3000"}, allowCredentials = "true")
@Controller
public class AuthenticationController {

    private final AuthenticationService authService;

    @MutationMapping
    public AuthenticationResponse registerUser(
            @Argument RegisterRequest request){

        return authService.register(request);
    }

    @MutationMapping
    public AuthenticationResponse authenticateUser(
            @Argument AuthenticationRequest request){

        return authService.authenticate(request);
    }
}
