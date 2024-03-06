package org.example.service;

import org.example.DTO.NewProduct;
import org.example.DTO.PageRequestDTO;
import org.example.entity.Product;
import org.springframework.data.domain.PageImpl;

import java.util.List;

public interface ProductService {
    PageImpl<Product> getAllProductsPaged(PageRequestDTO pageRequestDTO);

    Product getProductById(Long productId);

    Product addProduct(NewProduct product);

    Product addImageToProduct(Long productId, String imageLink);

    Product updateProduct(Long productId, NewProduct updatedProduct);

    Boolean deleteProduct(Long productId);

    PageImpl<Product> searchProductsPaged(String searchString, PageRequestDTO pageRequestDTO);

    PageImpl<Product> getAllProductsByCategoryIdsPaged(Iterable<Long> categoryIds, PageRequestDTO pageRequestDTO);

    List<Product> getAllProducts();
}
