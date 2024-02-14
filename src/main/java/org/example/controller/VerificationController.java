package org.example.controller;

import lombok.RequiredArgsConstructor;
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
        verificationService.verifyEmail(id);
        return "Successful verification!";
    }

    @PostMapping("/password")
    public String passwordRecovery(@RequestParam String id, @RequestBody String newPassword) {
        verificationService.passwordRecovery(id, newPassword);
        return "Successful verification!";
    }
}
