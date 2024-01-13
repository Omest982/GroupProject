package org.example.service;

import org.example.DTO.NewProduct;
import org.example.entity.Brand;
import org.example.entity.Category;
import org.example.entity.Product;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface ProductService {
    List<Product> getAllProducts();
    Product getProductById(Long id);
    List<Product> getAllProductsByBrand(Brand brand);
    List<Product> getAllProductsByBrands(Iterable<Brand> brands);
    Product addProduct(NewProduct product);
    Product addImageToProduct(Long productId, String imageLink);

    List<Product> getAllProductsByCategoryIds(Iterable<Long> categoryIds);

    Product updateProduct(Long productId, NewProduct updatedProduct);

    String deleteProduct(Long productId);
}
