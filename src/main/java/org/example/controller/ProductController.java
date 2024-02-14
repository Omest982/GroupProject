package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.DTO.NewProduct;
import org.example.DTO.PageRequestDTO;
import org.example.entity.Product;
import org.example.service.ProductService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageImpl;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class ProductController {

    private final ProductService productService;

    @QueryMapping
    public PageImpl<Product> getProductsByCategoryIdsPaged(@Argument Collection<Long> categoryIds,
                                                  @Argument PageRequestDTO pageRequestDTO){
        return productService.getAllProductsByCategoryIdsPaged(categoryIds, pageRequestDTO);
    }

    @QueryMapping
    public Product getProductById(@Argument Long id){
        return productService.getProductById(id);
    }

    @QueryMapping
    public PageImpl<Product> getAllProductsPaged(@Argument PageRequestDTO pageRequestDTO){
        return productService.getAllProductsPaged(pageRequestDTO);
    }

    @QueryMapping
    public List<Product> getAllProducts(){
        return productService.getAllProducts();
    }

    @QueryMapping
    public PageImpl<Product> searchProductsPaged(@Argument String searchString,
                                             @Argument PageRequestDTO pageRequestDTO){
        return productService.searchProductsPaged(searchString, pageRequestDTO);
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
