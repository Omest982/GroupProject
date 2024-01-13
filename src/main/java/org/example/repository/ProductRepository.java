package org.example.repository;

import org.example.entity.Brand;
import org.example.entity.Category;
import org.example.entity.Product;
import org.example.entity.ProductVariation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByCategoriesIn(Iterable<Category> categories);
    List<Product> findAllByBrandIn(Iterable<Brand> brand);
    List<Product> findAllByBrand(Brand brand);
}
