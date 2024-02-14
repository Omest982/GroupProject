package org.example.service;

import org.example.DTO.PasswordRecoveryDTO;

public interface VerificationService {
    Boolean verifyEmail(String userId);

    Boolean passwordRecovery(String userId, PasswordRecoveryDTO passwordRecoveryDTO);
}
