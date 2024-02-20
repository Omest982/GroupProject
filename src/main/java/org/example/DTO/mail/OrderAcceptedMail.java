package org.example.DTO.mail;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class OrderAcceptedMail extends MailMessage {
    private Long orderId;
}
