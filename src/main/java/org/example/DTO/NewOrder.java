package org.example.DTO;

import lombok.Data;
import org.example.entity.enums.PaymentMethod;

@Data
public class NewOrder {
    private String region;
    private String city;
    private String street;
    private String house;

    private String firstName;
    private String lastName;
    private String phoneNumber;

    private PaymentMethod paymentMethod;
    private String userComment;

}
