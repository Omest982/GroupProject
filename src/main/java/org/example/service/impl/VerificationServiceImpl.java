package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.DTO.PasswordRecoveryDTO;
import org.example.entity.User;
import org.example.service.UserService;
import org.example.service.VerificationService;
import org.example.utils.CryptoTool;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class VerificationServiceImpl implements VerificationService {
    private final CryptoTool cryptoTool;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    @Override
    @Transactional
    public Boolean verifyEmail(String userId) {
        Long id = cryptoTool.idOf(userId);

        User persistentUser = userService.getUserById(id);

        persistentUser.setIsEmailVerified(true);

        userService.saveUser(persistentUser);

        return true;
    }

    @Override
    @Transactional
    public Boolean passwordRecovery(String userId, PasswordRecoveryDTO passwordRecoveryDTO) {
        Long id = cryptoTool.idOf(userId);

        User persistentUser = userService.getUserById(id);

        log.info(passwordRecoveryDTO.getNewPassword());

        persistentUser.setPassword(passwordEncoder.encode(passwordRecoveryDTO.getNewPassword()));

        log.info(persistentUser.getPassword());

        userService.saveUser(persistentUser);

        return true;
    }
}
