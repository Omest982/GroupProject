package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.entity.User;
import org.example.entity.enums.UserRole;
import org.example.exception.UserAlreadyExistsException;
import org.example.exception.UserNotFoundException;
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
import java.util.ArrayList;

@RequiredArgsConstructor
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;
    @Override
    public AuthenticationResponse register(RegisterRequest request) {

        if (userService.getUserByEmail(request.getEmail()) != null){
            throw new UserAlreadyExistsException("User with email '" + request.getEmail() + "' already exists!");
        }

        User checkUser = userService.getUserByPhoneNumber(request.getPhoneNumber());

        if (checkUser != null){
            if (checkUser.getEmail() == null){
                checkUser.setEmail(request.getEmail());
                checkUser.setFirstName(request.getFirstName());
                checkUser.setLastName(request.getLastName());
                checkUser.setAddresses(new ArrayList<>());
                checkUser.setBirthdayDate(getParsedBirthdayDate(request));
                checkUser.setPassword(passwordEncoder.encode(request.getPassword()));
                checkUser.setUserRole(UserRole.CLIENT);

                return saveUserAndGetAuthResponse(checkUser);
            }else {
                throw new UserAlreadyExistsException("User with email '" + request.getEmail() + "' already exists!");
            }
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
                .addresses(new ArrayList<>())
                .build();

        return saveUserAndGetAuthResponse(transientUser);
    }

    private AuthenticationResponse saveUserAndGetAuthResponse(User user){
        User persistentUser = userService.saveUser(user);

        String token = JwtService.generateToken(persistentUser);

        return AuthenticationResponse.builder()
                .jwtToken(token)
                .build();
    }

    private LocalDate getParsedBirthdayDate(RegisterRequest request){
        LocalDate birthdayDate = null;
        if (request.getBirthdayDate() != null && !request.getBirthdayDate().equals("")){
            DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd.MM.uuuu");
            birthdayDate = LocalDate.parse(request.getBirthdayDate(), formatters);
        }
        return birthdayDate;
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        User persistentUser = userService.getUserByEmail(request.getEmail());

        if (persistentUser == null){
            throw new UserNotFoundException("User with email '" + request.getEmail() + "' not found!");
        }

        if (!passwordEncoder.matches(request.getPassword(), persistentUser.getPassword())){
            throw new UserNotFoundException("User with this password not found!");
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
