package org.example.repository;

import org.example.entity.VariationDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VariationDetailsRepository extends JpaRepository<VariationDetails, Long> {
    List<VariationDetails> findByProductVariationId(Long productVariationId);
}
