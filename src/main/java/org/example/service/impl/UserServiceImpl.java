package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.entity.User;
import org.example.exception.EntityNotFoundException;
import org.example.repository.UserRepository;
import org.example.security.jwt.JwtService;
import org.example.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Transactional
    @Override
    public User saveUser(User user){
        return userRepository.save(user);
    }

    @Override
    public User getUserByEmail(String email){
        return userRepository.findByEmail(email).orElseThrow(()
                -> new EntityNotFoundException(String.format("User with email %s not found", email)));
    }

    @Override
    public User getUserByEmailAndPassword(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password).orElseThrow(()
                -> new EntityNotFoundException("User with this email or password not found"));
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(()
                -> new EntityNotFoundException(String.format("User with id %s not found!", userId)));
    }

    @Override
    public User getUserByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber).orElseThrow(()
                -> new EntityNotFoundException(String.format("User with phone number %s not found", phoneNumber)));
    }

    @Override
    public User getUserByJwtToken(String jwtToken) {

        if(jwtService.isTokenExpired(jwtToken)){
            return null;
        }

        String userEmail = jwtService.extractEmail(jwtToken);

        return getUserByEmail(userEmail);
    }

    @Override
    public Boolean isUserExistsByEmailAndPhoneNumber(String email, String phoneNumber) {
        return userRepository.existsByEmailAndPhoneNumber(email, phoneNumber);
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        return getUserByEmail(email);
    }
}
