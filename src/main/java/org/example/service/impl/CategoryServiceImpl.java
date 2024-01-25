package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.entity.Category;
import org.example.repository.CategoryRepository;
import org.example.service.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Set;

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
    public List<Category> getAllCategoriesByNames(Iterable<String> names) {
        return categoryRepository.findAllByNameIn(names);
    }

    @Override
    public List<Category> getAllCategoriesByIds(Iterable<Long> categoryIds) {
        return categoryRepository.findAllById(categoryIds);
    }

    @Override
    public List<Category> getAllCategoriesByParentCategoryId(Long parentCategoryId) {
        return categoryRepository.findAllByParentCategoryId(parentCategoryId);
    }

    @Transactional
    @Override
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Transactional
    @Override
    public Category updateParentCategory(Long categoryId, Long parentCategoryId) {
        Category category = getCategoryById(categoryId);
        category.setParentCategoryId(parentCategoryId);
        return saveCategory(category);
    }

    @Transactional
    @Override
    public Category addCategory(String categoryName, Long parentCategoryId) {
        Category transientCategory = Category.builder()
                .name(categoryName)
                .parentCategoryId(parentCategoryId)
                .build();
        return saveCategory(transientCategory);
    }

    @Transactional
    @Override
    public String deleteCategory(Long categoryId) {
        categoryRepository.deleteById(categoryId);
        return "Successfully deleted category!";
    }

    @Override
    public List<Category> getAllHeadCategories() {
        return categoryRepository.findAllHeadCategories();
    }
}
