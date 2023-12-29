package org.example.DTO;

import lombok.Data;
import org.example.entity.enums.ShippingFrom;

import java.math.BigDecimal;

@Data
public class NewVariationDetails {
    private BigDecimal price;
    private BigDecimal sale;
    private ShippingFrom shippingFrom;
    private Long productVariationId;
}
