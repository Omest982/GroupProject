package org.example.entity.utils;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OneToOne;
import org.example.entity.Product;

import java.math.BigDecimal;

@MappedSuperclass
public class BusinessDetails {
    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;
    private int amount;
    private BigDecimal totalDetailPrice;
}
