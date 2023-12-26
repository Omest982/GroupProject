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
    public List<Category> getAllCategory() {
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
    public List<Category> getCategoriesByNames(List<String> names) {
        return categoryRepository.findByNameIn(names);
    }
}
