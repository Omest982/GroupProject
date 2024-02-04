package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.DTO.NewOrder;
import org.example.entity.User;
import org.example.entity.enums.UserRole;
import org.example.repository.UserRepository;
import org.example.security.jwt.JwtService;
import org.example.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Transactional
    @Override
    public User saveUser(User user){
        return userRepository.save(user);
    }

    @Override
    public User getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    @Override
    public User getUserByEmailAndPassword(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public User getUserByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber);
    }

    @Override
    public User getUserByJwtToken(String jwtToken) {

        if (JwtService.isTokenExpired(jwtToken)){
            throw new RuntimeException("");
        }

        String userEmail = JwtService.extractEmail(jwtToken);

        return getUserByEmail(userEmail);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email);
    }
}
