package org.example.DTO.mailMessages;

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
