package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.DTO.MailResponse;
import org.example.DTO.mailMessages.MailMessage;
import org.example.service.MailSenderService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class MailController {
    private final MailSenderService mailSenderService;

    @MutationMapping
    public MailResponse sendVerificationMail(@Argument MailMessage mailMessage){
        mailSenderService.sendEmailVerificationMail(mailMessage);

        return MailResponse.builder()
                .response("Sent!")
                .build();
    }

    @MutationMapping
    public MailResponse sendPasswordRecoveryMail(@Argument MailMessage mailMessage){
        mailSenderService.sendPasswordRecoveryMail(mailMessage);

        return MailResponse.builder()
                .response("Sent!")
                .build();
    }
}
