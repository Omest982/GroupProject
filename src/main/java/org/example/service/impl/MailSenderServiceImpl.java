package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.DTO.MailParams;
import org.example.service.MailSenderService;
import org.example.utils.CryptoTool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailSenderServiceImpl implements MailSenderService {
    private final JavaMailSender javaMailSender;
    private final CryptoTool cryptoTool;
    @Value("${service.verification.uri}")
    private String verificationServiceUri;
    @Value("${spring.mail.username}")
    private String emailFrom;
    @Override
    public void send(MailParams mailParams) {
        String subject = "Подтверждение почты";
        String messageBody = getActivationMailBody(mailParams.getId());
        String emailTo = mailParams.getEmailTo();

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(emailFrom);
        simpleMailMessage.setTo(emailTo);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(messageBody);

        javaMailSender.send(simpleMailMessage);
    }

    private String getActivationMailBody(Long id) {
        String msg = String.format("Для подтверждения вашей почты перейдите по ссылке:\n%s",
                verificationServiceUri);
        String hashedId = cryptoTool.hashOf(id);
        return msg.replace("{id}", hashedId);
    }
}
