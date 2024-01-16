package org.example.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode(exclude = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_details")
public class OrderDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
    @OneToOne
    @JoinColumn(name = "variation_details_id", nullable = false)
    private VariationDetails variationDetails;
    @Column(nullable = false)
    private Integer quantity;
    private BigDecimal totalDetailPrice;
}
