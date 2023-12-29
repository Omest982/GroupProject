package org.example.service;

import org.example.DTO.NewProduct;
import org.example.entity.Brand;
import org.example.entity.Category;
import org.example.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();
    Product getProductById(Long id);
    List<Product> getAllProductsByCategories(List<Category> categories);
    List<Product> getAllProductsByBrand(Brand brand);
    List<Product> getAllProductsByBrands(List<Brand> brands);
    Product addProduct(NewProduct product);

}
