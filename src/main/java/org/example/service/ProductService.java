package org.example.service;

import org.example.DTO.NewProduct;
import org.example.entity.Brand;
import org.example.entity.Category;
import org.example.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();
    Product getProductById(Long id);
    List<Product> getAllProductsByBrand(Brand brand);
    List<Product> getAllProductsByBrands(List<Brand> brands);
    Product addProduct(NewProduct product);
    Product addImageToProduct(Long productId, String imageLink);

    List<Product> getAllProductsByCategoryIds(List<Long> categoryIds);

    Product updateProduct(Long productId, NewProduct updatedProduct);

    String deleteProduct(Long productId);
}
