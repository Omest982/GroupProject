package org.example.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.entity.Category;
import org.example.repository.CategoryRepository;
import org.example.service.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Category getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(()
        -> new EntityNotFoundException(String.format("Category with id %s not found!", categoryId)));
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

        isParentCategoryReal(parentCategoryId);

        Category category = getCategoryById(categoryId);
        category.setParentCategoryId(parentCategoryId);
        return saveCategory(category);
    }

    private void isParentCategoryReal(Long parentCategoryId){
        if (parentCategoryId != null){
            Category parentCategory = getCategoryById(parentCategoryId);

            if (parentCategory == null){
                String errMsg = "Category can't be used as parent. Category with id " + parentCategoryId + " doesn't exist!";
                throw new EntityNotFoundException(errMsg);
            }
        }
    }

    @Transactional
    @Override
    public Category addCategory(String categoryName, Long parentCategoryId) {

        isParentCategoryReal(parentCategoryId);

        Category transientCategory = Category.builder()
                .name(categoryName)
                .parentCategoryId(parentCategoryId)
                .build();

        return saveCategory(transientCategory);
    }

    @Transactional
    @Override
    public Boolean deleteCategory(Long categoryId) {
        categoryRepository.deleteById(categoryId);
        return true;
    }

    @Override
    public List<Category> getAllHeadCategories() {
        return categoryRepository.findAllHeadCategories();
    }
}
