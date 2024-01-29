package org.example.DTO.mailMessages;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class VerificationMail extends MailMessage {
    private Long userId;
}
