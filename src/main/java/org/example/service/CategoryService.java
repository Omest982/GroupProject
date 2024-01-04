package org.example.service;

import org.example.entity.Category;

import java.util.List;

public interface CategoryService {

    List<Category> getAllCategories();

    Category getCategoryById(Long id);

    Category getCategoryByName(String categoryName);

    List<Category> getAllCategoriesByNames(List<String> categoryNames);

    List<Category> getAllCategoriesByIds(List<Long> categoryIds);

    List<Category> getAllCategoriesByParentCategoryId(Long parentCategoryId);

    Category saveCategory(Category category);
}
