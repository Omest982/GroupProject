package org.example.service;

import org.example.entity.User;
import org.example.exception.EntityNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User saveUser(User user);

    User getUserByEmail(String email) throws EntityNotFoundException;

    User getUserByEmailAndPassword(String email, String password);

    User getUserById(Long userId);

    User getUserByPhoneNumber(String phoneNumber);

    User getUserByJwtToken(String jwtToken);

    Boolean isUserExistsByEmailOrPhoneNumber(String email, String phoneNumber);
}
