package org.example.DTO;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewShippingInfo {
    private String region;
    private String city;
    private String street;
    private String house;
    private String recipientFirstName;
    private String recipientLastName;
    private String recipientPhoneNumber;
}
