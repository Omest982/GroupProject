package org.example.service;

import org.example.DTO.mail.MailMessage;
import org.example.DTO.mail.OrderAcceptedMail;

public interface MailSenderService {
    void sendEmailVerificationMail(MailMessage mailMessage);

    void sendOrderAcceptedMail(OrderAcceptedMail orderAcceptedMail);

    void sendPasswordRecoveryMail(MailMessage mailMessage);

}
