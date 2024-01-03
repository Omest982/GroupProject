package org.example.DTO;

import lombok.Data;

@Data
public class NewOrder {
    private String region;
    private String city;
    private String street;
    private String house;

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
}
