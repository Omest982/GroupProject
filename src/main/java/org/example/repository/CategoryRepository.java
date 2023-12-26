package org.example.repository;

import org.example.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByNameIn(List<String> names);
    Category findByName(String name);
}
