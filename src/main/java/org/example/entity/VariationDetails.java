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
    @Column(nullable = false)
    private BigDecimal price;
    @Column(nullable = false)
    private BigDecimal sale;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ShippingFrom shippingFrom;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_variation_id", nullable = false)
    private ProductVariation productVariation;
}
