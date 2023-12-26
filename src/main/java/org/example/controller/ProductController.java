package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.DTO.NewProduct;
import org.example.entity.Brand;
import org.example.entity.Category;
import org.example.entity.Product;
import org.example.service.CategoryService;
import org.example.service.ProductService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@RequiredArgsConstructor
@CrossOrigin(origins = {"http://13.51.170.112:8080", "http://localhost:3000"}, allowCredentials = "true")
@Controller
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    @QueryMapping
    public List<Product> getProductsByCategories(@Argument List<String> categoryNames){
        List<Category> categoryList = categoryService.getCategoriesByNames(categoryNames);
        List<Product> products = productService.getProductsByCategories(categoryList);
        return products;
    }

    @QueryMapping
    public Product getProductById(@Argument Long id){
        return productService.getProductById(id);
    }

    @QueryMapping
    public List<Product> getAllProducts(){
        return productService.getAllProducts();
    }

    @MutationMapping
    public Product addProduct(@Argument NewProduct product){
        return productService.addProduct(product);
    }
}
