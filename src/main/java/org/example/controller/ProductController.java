package org.example.controller;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.example.entity.Brand;
import org.example.entity.Product;
import org.example.repository.BrandRepository;
import org.example.repository.CategoryRepository;
import org.example.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping(value = "/product")
@RequiredArgsConstructor
@RestController
public class ProductController {
    private final ProductService productService;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final EntityManager entityManager;

    @GetMapping(value = "/all")
    public List<Product> getAllProducts(){
        return productService.getAllProducts();
    }
    @GetMapping(value = "/{id}")
    public Product getProductById(@PathVariable("id") Long id){
        return productService.getProductById(id);
    }
    @GetMapping(value = "/category")
    public List<Product> getProductByCategories(@RequestParam("category") String category, @RequestParam(required = false, name = "brands") String brands) {
        System.out.println(brands);
        List<Brand> brandsPersistent = new ArrayList<>();
        StringBuilder productQuery = new StringBuilder("SELECT * FROM product");
        StringBuilder brandQuery = new StringBuilder("SELECT id FROM brand");
        if(brands.length() > 0) {
            String[] brandsTransient = brands.split(" ");
            brandQuery.append(" WHERE name IN (");
            for(String str : brandsTransient){
                brandQuery.append("'" + str + "'" + ",");
            }
            brandQuery.deleteCharAt(brandQuery.length() - 1);
            brandQuery.append(")");

            System.out.println(brandQuery);
            Query q = entityManager.createNativeQuery(brandQuery.toString(), Long.class);
            List<Long> brandIds = q.getResultList();
            System.out.println(brandIds);
            if(brandIds.size() > 0){
                productQuery.append(" WHERE brand_id IN (");
                for(Long num : brandIds){
                    productQuery.append(num + ",");
                }
                productQuery.deleteCharAt(productQuery.length() - 1);
                productQuery.append(")");
            }
        }
        Query q = entityManager.createNativeQuery(productQuery.toString(), Product.class);
        List<Product> products = q.getResultList();
        System.out.println(products);
        //Category category1 = categoryRepository.findByName(category);
        return products;
    }
}
