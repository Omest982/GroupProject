package org.example.controller;

import org.example.entity.*;
import org.example.entity.enums.Classification;
import org.example.entity.enums.ProductStatus;
import org.example.entity.enums.Sex;
import org.example.entity.enums.ShippingFrom;
import org.example.service.BrandService;
import org.example.service.CategoryService;
import org.example.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.GraphQlTester;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@AutoConfigureGraphQlTester
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

    @Autowired
    private GraphQlTester graphQlTester;

    @Test
    void shouldAddProduct(){

        Map<String, Object> newProduct = new HashMap<>();
        newProduct.put("name", "Calvin Klein Euphoria");
        newProduct.put("productGroup", "Парфумована вода");
        newProduct.put("brandId", 1L);
        newProduct.put("categoryId", 2L);
        newProduct.put("countriesMadeInIds", List.of(1L));
        newProduct.put("countryTradeMarkId", 1L);
        newProduct.put("imageLinks", Set.of("Fake!"));
        newProduct.put("sex", Sex.FEMALE);
        newProduct.put("productStatus", ProductStatus.AVAILABLE);
        newProduct.put("classification", Classification.ELITE);
        newProduct.put("description", "Cool!");

        graphQlTester.documentName("product_add")
                .variable("newProduct", newProduct)
                .execute()
                .path("addProduct")
                .entity(Product.class)
                .satisfies(product -> {
                    Assertions.assertEquals(1, product.getId());
                    Assertions.assertEquals("Calvin Klein Euphoria", product.getName());
                    Assertions.assertEquals("Lacoste", product.getBrand().getName());
                    List<Category> categoryList = product.getCategories().stream().toList();
                    Assertions.assertEquals("Men perfumery", categoryList.get(0).getName());
                    Assertions.assertEquals("Perfumery", categoryList.get(1).getName());
                    Assertions.assertEquals("Ukraine", product.getCountriesMadeIn().stream().toList().get(0).getName());
                    Assertions.assertEquals("Ukraine", product.getCountryTradeMark().getName());
                    Assertions.assertEquals("Fake!", product.getImages().stream().toList().get(0).getImageLink());
                    Assertions.assertEquals(1, product.getImages().stream().toList().get(0).getId());
                });
    }

    @Nested
    class ProductVariationControllerTest {
        @Test
        void shouldAddProductVariation(){

            Map<String, Object> newProductVariation = new HashMap<>();
            newProductVariation.put("imageLink", "Fake image!");
            newProductVariation.put("variationName", "100ml");
            newProductVariation.put("productId", 1L);

            graphQlTester.documentName("product_variation_add")
                    .variable("productVariation", newProductVariation)
                    .execute()
                    .path("addProductVariation")
                    .entity(ProductVariation.class)
                    .satisfies(productVariation -> {
                       Assertions.assertEquals(1L, productVariation.getId());
                        Assertions.assertEquals("100ml", productVariation.getVariationName());
                    });
        }

        @Nested
        class VariationDetailsControllerTest {
            @Test
            void shouldAddVariationDetails(){

                Map<String, Object> newVariationDetails = new HashMap<>();
                newVariationDetails.put("price", BigDecimal.valueOf(100));
                newVariationDetails.put("sale", BigDecimal.valueOf(20));
                newVariationDetails.put("shippingFrom", ShippingFrom.UA);
                newVariationDetails.put("productVariationId", 1);

                graphQlTester.documentName("variation_details_add")
                        .variable("variationDetails", newVariationDetails)
                        .execute()
                        .path("addVariationDetails")
                        .entity(VariationDetails.class)
                        .satisfies(variationDetails -> {
                            Assertions.assertEquals(1L, variationDetails.getId());
                            Assertions.assertEquals(BigDecimal.valueOf(100), variationDetails.getPrice());
                            Assertions.assertEquals(BigDecimal.valueOf(20), variationDetails.getSale());
                        });
            }
        }
    }
}
