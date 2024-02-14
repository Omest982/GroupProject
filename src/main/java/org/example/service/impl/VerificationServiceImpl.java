package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.entity.User;
import org.example.service.UserService;
import org.example.service.VerificationService;
import org.example.utils.CryptoTool;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VerificationServiceImpl implements VerificationService {
    private final CryptoTool cryptoTool;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    @Override
    @Transactional
    public User verifyEmail(String userId) {
        Long id = cryptoTool.idOf(userId);

        User persistentUser = userService.getUserById(id);

        persistentUser.setIsEmailVerified(true);

        return userService.saveUser(persistentUser);
    }

    @Override
    @Transactional
    public User passwordRecovery(String userId, String newPassword) {
        Long id = cryptoTool.idOf(userId);

        User persistentUser = userService.getUserById(id);

        persistentUser.setPassword(passwordEncoder.encode(newPassword));

        return userService.saveUser(persistentUser);
    }
}
