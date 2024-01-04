package org.example.DTO;

import lombok.Data;

@Data
public class NewOrderDetails {
    private Long orderId;
    private Long variationDetailsId;
    private int quantity;
}
