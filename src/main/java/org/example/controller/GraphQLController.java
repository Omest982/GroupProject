package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.entity.Brand;
import org.example.entity.Category;
import org.example.entity.Product;
import org.example.repository.BrandRepository;
import org.example.repository.CategoryRepository;
import org.example.repository.ProductRepository;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class GraphQLController{
    private final BrandRepository brandRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @QueryMapping
    public Brand getBrandById(@Argument Long id){
        return brandRepository.findById(id).orElse(null);
    }

    @QueryMapping
    public List<Brand> getAllBrands(){
        return brandRepository.findAll();
    }

    @QueryMapping
    public List<Product> getProductsByCategories(@Argument List<String> categoryNames){
        List<Category> categoryList = categoryRepository.findByNameIn(categoryNames);
        List<Product> products = productRepository.findByCategoriesIn(categoryList);
        return products;
    }

    @QueryMapping
    public Product getProductById(@Argument Long id){
        return productRepository.findById(id).orElse(null);
    }

    @QueryMapping
    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

}
