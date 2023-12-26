package org.example.repository;

import org.example.entity.Brand;
import org.example.entity.Category;
import org.example.entity.Product;
import org.example.entity.ProductVariation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoriesIn(List<Category> categories);
    List<Product> findByBrandIn(List<Brand> brand);
    List<Product> findByBrand(Brand brand);
}
