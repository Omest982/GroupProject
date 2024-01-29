package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.DTO.mailMessages.VerificationMail;
import org.example.DTO.mailMessages.OrderAcceptedMail;
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
    public void sendEmailVerificationMail(VerificationMail verificationMail) {
        String subject = "Email verification";
        String messageBody = getVerificationMailBody(verificationMail.getUserId());
        String emailTo = verificationMail.getEmailTo();

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(emailFrom);
        simpleMailMessage.setTo(emailTo);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(messageBody);

        javaMailSender.send(simpleMailMessage);
    }

    @Override
    public void sendOrderAcceptedMail(OrderAcceptedMail orderAcceptedParams) {
        String subject = "Makeup order confirmed!";
        String messageBody = getOrderAcceptedBody(orderAcceptedParams.getOrderId());
        String emailTo = orderAcceptedParams.getEmailTo();

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(emailFrom);
        simpleMailMessage.setTo(emailTo);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(messageBody);


        System.out.println(messageBody);
        System.out.println(emailTo);

        javaMailSender.send(simpleMailMessage);
    }

    private String getVerificationMailBody(Long userId) {
        String msg = String.format("Для подтверждения вашей почты перейдите по ссылке:\n%s",
                verificationServiceUri);
        String hashedUserId = cryptoTool.hashOf(userId);
        return msg.replace("{id}", hashedUserId);
    }

    private String getOrderAcceptedBody(Long orderId){
        return "Your order with id " + orderId + " was confirmed!";
    }
}
