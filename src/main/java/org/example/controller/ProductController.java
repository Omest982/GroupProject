package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.DTO.NewProduct;
import org.example.entity.Product;
import org.example.service.CategoryService;
import org.example.service.ProductService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Controller
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    @QueryMapping
    public List<Product> getProductsByCategoryIds(@Argument Iterable<Long> categoryIds){
        return productService.getAllProductsByCategoryIds(categoryIds);
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

    @MutationMapping
    public Product updateProduct(@Argument Long productId, @Argument NewProduct updatedProduct){
        return productService.updateProduct(productId, updatedProduct);
    }

    @MutationMapping
    public String deleteProduct(@Argument Long productId){
        return productService.deleteProduct(productId);
    }

    @MutationMapping
    public Product addImageToProduct(@Argument Long productId, @Argument String imageLink){
        return productService.addImageToProduct(productId, imageLink);
    }

}
