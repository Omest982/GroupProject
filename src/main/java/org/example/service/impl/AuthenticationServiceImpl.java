package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.DTO.security.AuthenticationRequest;
import org.example.DTO.security.AuthenticationResponse;
import org.example.DTO.security.RegisterRequest;
import org.example.entity.User;
import org.example.entity.enums.UserRole;
import org.example.exception.EntityAlreadyExistsException;
import org.example.exception.EntityNotFoundException;
import org.example.security.jwt.JwtService;
import org.example.service.AuthenticationService;
import org.example.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
@Slf4j
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;
    private final JwtService jwtService;
    @Transactional
    @Override
    public Boolean register(RegisterRequest request) {

        if (userService.isUserExistsByEmailOrPhoneNumber(request.getEmail(), request.getPhoneNumber())){
            throw new EntityAlreadyExistsException("User with this email or phone number already exists!");
        }

        

        User transientUser = User.builder()
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .password(passwordEncoder.encode(request.getPassword()))
                .phoneNumber(request.getPhoneNumber())
                .birthdayDate(getParsedBirthdayDate(request))
                .isEmailVerified(false)
                .userRole(UserRole.CLIENT)
                .enabled(true)
                .build();

        User persistentUser = userService.saveUser(transientUser);

        log.info(String.format("Registered user with %s email", persistentUser.getEmail()));

        return true;
    }

    private LocalDate getParsedBirthdayDate(RegisterRequest request){
        LocalDate birthdayDate = null;
        if (request.getBirthdayDate() != null && !request.getBirthdayDate().equals("")){
            DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd.MM.uuuu");
            birthdayDate = LocalDate.parse(request.getBirthdayDate(), formatters);
        }
        return birthdayDate;
    }

    @Transactional
    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {

         Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        String token = jwtService.generateToken(authentication);

        log.info(String.format("Authenticated user with %s email", request.getEmail()));

        return AuthenticationResponse.builder()
                .jwtToken(token)
                .build();
    }
}
