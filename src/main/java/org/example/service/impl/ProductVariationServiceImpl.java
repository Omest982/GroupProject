package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.DTO.NewProductVariation;
import org.example.entity.Image;
import org.example.entity.Product;
import org.example.entity.ProductVariation;
import org.example.repository.ProductVariationRepository;
import org.example.service.ImageService;
import org.example.service.ProductService;
import org.example.service.ProductVariationService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductVariationServiceImpl implements ProductVariationService {
    private final ProductVariationRepository productVariationRepository;
    private final ImageService imageService;
    private final ProductService productService;

    @Override
    public List<ProductVariation> getAllProductVariations() {
        return productVariationRepository.findAll();
    }

    @Override
    public ProductVariation getProductVariationById(Long productVariationId) {
        return productVariationRepository.findById(productVariationId).orElse(null);
    }

    @Override
    public List<ProductVariation> getAllProductVariationsByProductId(Long productId) {
        return productVariationRepository.findAllByProductId(productId);
    }

    @Override
    public ProductVariation addProductVariation(NewProductVariation productVariation) {
        Image variationImage = imageService.addImage(productVariation.getImageLink());
        Product product = productService.getProductById(productVariation.getProductId());

        ProductVariation transientProductVariation = ProductVariation.builder()
                .variationImage(variationImage)
                .amount(productVariation.getAmount())
                .product(product)
                .variationDetails(new ArrayList<>())
                .build();

        return productVariationRepository.save(transientProductVariation);
    }
}
