package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.security.DTO.AuthenticationRequest;
import org.example.security.DTO.AuthenticationResponse;
import org.example.security.DTO.RegisterRequest;
import org.example.service.AuthenticationService;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@CrossOrigin(origins = {"http://13.51.170.112:8080", "http://localhost:3000"}, allowCredentials = "true")
@Controller
public class AuthenticationController {

    private final AuthenticationService authService;

    @MutationMapping
    public ResponseEntity<AuthenticationResponse> registerUser(
            @RequestBody RegisterRequest request){

        AuthenticationResponse response = authService.register(request);

        if (response == null){
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(response);
    }

    @MutationMapping
    public ResponseEntity<AuthenticationResponse> authenticateUser(
            @RequestBody AuthenticationRequest request){

        AuthenticationResponse response = authService.authenticate(request);

        if (response == null){
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(response);
    }
}
