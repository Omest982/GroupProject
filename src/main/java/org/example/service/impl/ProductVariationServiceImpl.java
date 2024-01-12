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
        Image variationImage = imageService.addOrGetImage(productVariation.getImageLink());
        Product product = productService.getProductById(productVariation.getProductId());

        ProductVariation transientProductVariation = ProductVariation.builder()
                .variationImage(variationImage)
                .variationName(productVariation.getVariationName())
                .product(product)
                .variationDetails(new ArrayList<>())
                .build();

        return productVariationRepository.save(transientProductVariation);
    }

    @Override
    public ProductVariation updateProductVariation(Long productVariationId, NewProductVariation updatedProductVariation) {
        Image variationImage = imageService.addOrGetImage(updatedProductVariation.getImageLink());
        Product product = productService.getProductById(updatedProductVariation.getProductId());

        ProductVariation productVariation = getProductVariationById(productVariationId);

        productVariation.setProduct(product);
        productVariation.setVariationImage(variationImage);
        productVariation.setVariationName(updatedProductVariation.getVariationName());

        return productVariationRepository.save(productVariation);
    }

    @Override
    public String deleteProductVariation(Long productVariationId) {
        productVariationRepository.deleteById(productVariationId);
        if (getProductVariationById(productVariationId) == null){
            return "Successfully deleted product variation!";
        }
        return "Failed to delete product variation!";
    }
}
