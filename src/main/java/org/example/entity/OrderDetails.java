package org.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
    @JoinColumn(name = "order_id")
    @NotNull
    private Order order;
    @OneToOne
    @JoinColumn(name = "variation_details_id")
    @NotNull
    private VariationDetails variationDetails;
    @NotNull
    private Integer quantity;
    private BigDecimal totalDetailPrice;
}
