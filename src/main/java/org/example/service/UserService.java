package org.example.service;

import org.example.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User saveUser(User user);

    User getUserByEmail(String email);

    User getUserByEmailAndPassword(String email, String password);

    User getUserById(Long userId);

    User getUserByPhoneNumber(String phoneNumber);
}
