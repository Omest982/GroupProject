package org.example.controller;

import org.example.DTO.NewProduct;
import org.example.entity.Brand;
import org.example.entity.Category;
import org.example.entity.Country;
import org.example.entity.Product;
import org.example.entity.enums.Classification;
import org.example.entity.enums.ProductStatus;
import org.example.entity.enums.Sex;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.GraphQlTester;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@AutoConfigureGraphQlTester
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
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

}
