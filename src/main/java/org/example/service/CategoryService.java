package org.example.service;

import org.example.entity.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategory();
    Category getCategoryById(Long id);
    Category getCategoryByName(String categoryName);
    List<Category> getCategoriesByNames(List<String> categoryNames);
}
