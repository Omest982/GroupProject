package org.example.DTO;

import lombok.Data;

@Data
public class NewBasketDetails {
    private Long basketId;
    private Long variationDetailsId;
    private int quantity;
}
