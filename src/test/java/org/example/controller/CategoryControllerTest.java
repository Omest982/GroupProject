package org.example.controller;

import org.example.entity.Category;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.GraphQlTester;

@AutoConfigureGraphQlTester
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class CategoryControllerTest {
    @Autowired
    private GraphQlTester graphQlTester;
    @Test
    @Order(1)
    void addCategory(){
        graphQlTester.documentName("category_add")
                .variable("categoryName", "Perfumery")
                .execute()
                .path("addCategory")
                .entity(Category.class)
                .satisfies(category -> {
                    Assertions.assertEquals("Perfumery", category.getName());
                    Assertions.assertEquals(1, category.getId());
                });
    }

    @Test
    @Order(2)
    void addCategoryWithParent(){
        graphQlTester.documentName("category_add_with_parent")
                .variable("categoryName", "Men perfumery")
                .variable("parentCategoryId", 1)
                .execute()
                .path("addCategory")
                .entity(Category.class)
                .satisfies(category -> {
                    Assertions.assertEquals("Men perfumery", category.getName());
                    Assertions.assertEquals(1, category.getParentCategoryId());
                });
    }
}
