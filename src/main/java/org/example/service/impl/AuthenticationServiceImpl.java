package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.entity.User;
import org.example.entity.enums.UserRole;
import org.example.security.DTO.AuthenticationRequest;
import org.example.security.DTO.AuthenticationResponse;
import org.example.security.DTO.RegisterRequest;
import org.example.security.jwt.JwtService;
import org.example.service.AuthenticationService;
import org.example.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;
    @Override
    public AuthenticationResponse register(RegisterRequest request) {

        if (userService.getUserByEmail(request.getEmail()) != null){
            return null;
            //TODO: Make an exception instead of null
        }

        LocalDate birthdayDate = null;

        if (request.getBirthdayDate() != null && !request.getBirthdayDate().equals("")){
            DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd.MM.uuuu");
            birthdayDate = LocalDate.parse(request.getBirthdayDate(), formatters);
        }

        User transientUser = User.builder()
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .password(passwordEncoder.encode(request.getPassword()))
                .phoneNumber(request.getPhoneNumber())
                .birthdayDate(birthdayDate)
                .isEmailVerified(false)
                .userRole(UserRole.CLIENT)
                .enabled(true)
                .build();

        User persistentUser = userService.saveUser(transientUser);

        String token = JwtService.generateToken(persistentUser);

        return AuthenticationResponse.builder()
                .jwtToken(token)
                .build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        User persistentUser = userService.getUserByEmail(request.getEmail());

        if (persistentUser == null){
            return null;
            //TODO: Make an exception instead of null
        }

        if (!passwordEncoder.matches(request.getPassword(), persistentUser.getPassword())){
            return null;
        }

        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        String token = JwtService.generateToken(persistentUser);

        return AuthenticationResponse.builder()
                .jwtToken(token)
                .build();
    }
}
