package org.example.repository;

import org.example.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllByNameIn(Iterable<String> names);
    List<Category> findAllByParentCategoryId(Long parentCategoryId);

    @Query("""
           SELECT c FROM Category c
           WHERE parentCategoryId IS NULL            
        """
    )
    List<Category> findAllHeadCategories();
}
