package org.example.entity.utils;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OneToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.example.entity.Product;
import org.example.entity.VariationDetails;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Setter
@MappedSuperclass
public class BusinessDetails {
    @OneToOne
    @JoinColumn(name = "variation_details_id")
    private VariationDetails variationDetails;
    private int quantity;
    private BigDecimal totalDetailPrice;
}
