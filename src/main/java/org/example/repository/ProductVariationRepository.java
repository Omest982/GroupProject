package org.example.repository;

import org.example.entity.ProductVariation;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductVariationRepository extends JpaRepository<ProductVariation, Long> {
}
