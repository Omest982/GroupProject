package org.example.service;

import org.example.entity.Category;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface CategoryService {

    List<Category> getAllCategories();

    Category getCategoryById(Long categoryId);

    List<Category> getAllCategoriesByIds(Iterable<Long> categoryIds);

    List<Category> getAllCategoriesByParentCategoryId(Long parentCategoryId);

    Category saveCategory(Category category);

    Category updateParentCategory(Long categoryId, Long parentCategoryId);

    Category addCategory(String categoryName, Long parentCategoryId);

    String deleteCategory(Long categoryId);

    List<Category> getAllHeadCategories();
}
