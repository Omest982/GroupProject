package org.example.service;

import org.example.DTO.mailMessages.MailMessage;
import org.example.DTO.mailMessages.OrderAcceptedMail;

public interface MailSenderService {
    void sendEmailVerificationMail(MailMessage mailMessage);

    void sendOrderAcceptedMail(OrderAcceptedMail orderAcceptedMail);

    void sendPasswordRecoveryMail(MailMessage mailMessage);

}
