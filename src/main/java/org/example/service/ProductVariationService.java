package org.example.service;

import org.example.DTO.NewProductVariation;
import org.example.entity.ProductVariation;

import java.util.List;

public interface ProductVariationService {
    List<ProductVariation> getAllProductVariations();
    ProductVariation getProductVariationById(Long productVariationId);
    List<ProductVariation> getAllProductVariationsByProductId(Long productId);
    ProductVariation addProductVariation(NewProductVariation productVariation);
}
