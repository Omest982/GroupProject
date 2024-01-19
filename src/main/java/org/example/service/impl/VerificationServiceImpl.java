package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.entity.User;
import org.example.service.UserService;
import org.example.service.VerificationService;
import org.example.utils.CryptoTool;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VerificationServiceImpl implements VerificationService {
    private final CryptoTool cryptoTool;
    private final UserService userService;
    @Override
    public User verifyEmail(String userId) {
        Long id = cryptoTool.idOf(userId);

        User persistentUser = userService.getUserById(id);

        persistentUser.setIsEmailVerified(true);

        return userService.saveUser(persistentUser);
    }
}
