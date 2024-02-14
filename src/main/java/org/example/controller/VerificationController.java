package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.DTO.PasswordRecoveryDTO;
import org.example.entity.User;
import org.example.service.UserService;
import org.example.service.VerificationService;
import org.example.utils.CryptoTool;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/verification")
@RequiredArgsConstructor
public class VerificationController {

    private final VerificationService verificationService;

    @GetMapping("/email")
    public String verifyEmail(@RequestParam String id) {

        if (verificationService.verifyEmail(id)){
            return "Successful verification!";
        }

        return "Failed to verify!";
    }

    @PostMapping("/password")
    public String passwordRecovery(@RequestParam String id, @RequestBody PasswordRecoveryDTO passwordRecoveryDTO) {

        if (verificationService.passwordRecovery(id, passwordRecoveryDTO)){
            return "Successful recovery!";
        }

        return "Failed to recover!";
    }
}
