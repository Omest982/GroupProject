package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.DTO.NewProductVariation;
import org.example.entity.ProductVariation;
import org.example.service.ProductVariationService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProductVariationController {
    private final ProductVariationService productVariationService;

    @QueryMapping
    public List<ProductVariation> getProductVariationsByProductId(@Argument Long productId){
        return productVariationService.getAllProductVariationsByProductId(productId);
    }

    @MutationMapping
    public ProductVariation addProductVariation(@Argument NewProductVariation productVariation){
        return productVariationService.addProductVariation(productVariation);
    }
}
