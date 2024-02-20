package org.example.service;

import org.example.DTO.security.AuthenticationRequest;
import org.example.DTO.security.AuthenticationResponse;
import org.example.DTO.security.RegisterRequest;

public interface AuthenticationService {
    AuthenticationResponse register(RegisterRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request);
}
