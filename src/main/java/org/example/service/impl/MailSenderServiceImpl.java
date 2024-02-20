package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.DTO.mail.MailMessage;
import org.example.DTO.mail.OrderAcceptedMail;
import org.example.entity.User;
import org.example.service.MailSenderService;
import org.example.service.UserService;
import org.example.utils.CryptoTool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailSenderServiceImpl implements MailSenderService {
    private final UserService userService;
    private final JavaMailSender javaMailSender;
    private final CryptoTool cryptoTool;
    @Value("${service.verification.uri}")
    private String verificationServiceUri;
    @Value("${service.password-recovery.uri}")
    private String passwordServiceUri;
    @Value("${spring.mail.username}")
    private String emailFrom;
    @Override
    public void sendEmailVerificationMail(MailMessage mailMessage) {
        String subject = "Email verification";
        String emailTo = mailMessage.getEmailTo();
        User user = userService.getUserByEmail(emailTo);
        String messageBody = getVerificationMailBody(user.getId());

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(emailFrom);
        simpleMailMessage.setTo(emailTo);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(messageBody);

        javaMailSender.send(simpleMailMessage);
    }

    @Override
    public void sendOrderAcceptedMail(OrderAcceptedMail orderAcceptedMail) {
        String subject = "Makeup order confirmed!";
        String messageBody = getOrderAcceptedBody(orderAcceptedMail.getOrderId());
        String emailTo = orderAcceptedMail.getEmailTo();

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(emailFrom);
        simpleMailMessage.setTo(emailTo);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(messageBody);

        System.out.println(messageBody);
        System.out.println(emailTo);

        javaMailSender.send(simpleMailMessage);
    }

    @Override
    public void sendPasswordRecoveryMail(MailMessage mailMessage) {
        String subject = "Password recovery";
        String emailTo = mailMessage.getEmailTo();
        User user = userService.getUserByEmail(emailTo);
        String messageBody = getPasswordRecoveryBody(user.getId());

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(emailFrom);
        simpleMailMessage.setTo(emailTo);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(messageBody);

        javaMailSender.send(simpleMailMessage);
    }

    private String getVerificationMailBody(Long userId) {
        String msg = String.format("Для подтверждения вашей почты перейдите по ссылке:\n%s",
                verificationServiceUri);
        String hashedUserId = cryptoTool.hashOf(userId);
        return msg.replace("{id}", hashedUserId);
    }

    private String getPasswordRecoveryBody(Long userId) {
        String msg = String.format("Для ввода нового пароля перейдите по ссылке:\n%s",
                passwordServiceUri);
        String hashedUserId = cryptoTool.hashOf(userId);
        return msg.replace("{id}", hashedUserId);
    }

    private String getOrderAcceptedBody(Long orderId){
        return "Your order with id " + orderId + " was confirmed!";
    }
}
