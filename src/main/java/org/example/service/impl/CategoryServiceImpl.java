package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.entity.Category;
import org.example.repository.CategoryRepository;
import org.example.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public List<Category> getAllCategoriesByNames(List<String> names) {
        return categoryRepository.findByNameIn(names);
    }

    @Override
    public List<Category> getAllCategoriesByIds(List<Long> categoryIds) {
        return categoryRepository.findAllById(categoryIds);
    }

    @Override
    public List<Category> getAllCategoriesByParentCategoryId(Long parentCategoryId) {
        return categoryRepository.findByParentCategoryId(parentCategoryId);
    }

    @Override
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }
}
