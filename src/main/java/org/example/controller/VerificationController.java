package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.DTO.PasswordRecoveryDTO;
import org.example.entity.User;
import org.example.service.UserService;
import org.example.service.VerificationService;
import org.example.utils.CryptoTool;
import org.springframework.web.bind.annotation.*;

import java.util.AbstractSequentialList;

@RestController
@RequestMapping("/verification")
@RequiredArgsConstructor
public class VerificationController {

    private final VerificationService verificationService;

    @GetMapping("/email")
    public Boolean verifyEmail(@RequestParam String id) {

        return verificationService.verifyEmail(id);
    }

    @PostMapping("/password")
    public Boolean passwordRecovery(@RequestParam String id, @RequestBody PasswordRecoveryDTO passwordRecoveryDTO) {

        return verificationService.passwordRecovery(id, passwordRecoveryDTO);
    }
}
