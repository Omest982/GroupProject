package org.example.service;

import org.example.entity.User;

public interface VerificationService {
    User verifyEmail(String userId);

    User passwordRecovery(String userId, String newPassword);
}
