package org.example.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.entity.enums.ShippingFrom;

import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode(exclude = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "variation_details")
public class VariationDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal price;
    private BigDecimal sale;
    @Enumerated(EnumType.STRING)
    private ShippingFrom shippingFrom;
}
