package org.example.service;

import org.example.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User saveUser(User user);

    User getByUsername(String username);
}
