package org.example.service;

import org.example.DTO.NewProduct;
import org.example.DTO.PageRequestDTO;
import org.example.entity.Brand;
import org.example.entity.Category;
import org.example.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface ProductService {
    Page<Product> getAllProductsPaged(PageRequestDTO pageRequestDTO);

    Product getProductById(Long id);

    Product addProduct(NewProduct product);

    Product addImageToProduct(Long productId, String imageLink);

    Product updateProduct(Long productId, NewProduct updatedProduct);

    String deleteProduct(Long productId);

    Page<Product> searchProductsPaged(String searchString, PageRequestDTO pageRequestDTO);

    Page<Product> getAllProductsByCategoryIdsPaged(Iterable<Long> categoryIds, PageRequestDTO pageRequestDTO);
}
