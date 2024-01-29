package org.example.service;

import org.example.DTO.mailMessages.VerificationMail;
import org.example.DTO.mailMessages.OrderAcceptedMail;

public interface MailSenderService {
    void sendEmailVerificationMail(VerificationMail verificationMail);

    void sendOrderAcceptedMail(OrderAcceptedMail orderAcceptedParams);

}
