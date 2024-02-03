package org.example.DTO;

import lombok.*;
import org.example.entity.enums.ShippingFrom;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewVariationDetails {
    private BigDecimal price;
    private BigDecimal sale;
    private ShippingFrom shippingFrom;
    private Long productVariationId;
}
