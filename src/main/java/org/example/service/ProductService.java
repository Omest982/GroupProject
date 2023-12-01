package org.example.service;

import org.example.entity.Brand;
import org.example.entity.Category;
import org.example.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();
    Product getProductById(Long id);
//    List<Product> getProductsByCategories(List<Category> category);
//    List<Product> getProductsByBrand(Brand brand);
//    List<Product> getProductsByBrands(List<Brand> brands);

}
