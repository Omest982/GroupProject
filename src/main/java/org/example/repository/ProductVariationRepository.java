package org.example.repository;

import org.example.entity.ProductVariation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ProductVariationRepository extends JpaRepository<ProductVariation, Long> {
    List<ProductVariation> findByProductId(Long productId);
}
