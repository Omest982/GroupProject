package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.DTO.MailParams;
import org.example.service.MailSenderService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class MailController {
    private final MailSenderService mailSenderService;

    @QueryMapping
    public String sendActivationMail(@Argument MailParams mailParams){
        mailSenderService.send(mailParams);
        return "Sent!";
    }
}
