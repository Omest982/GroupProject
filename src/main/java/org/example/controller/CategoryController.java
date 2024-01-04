package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.entity.Category;
import org.example.service.CategoryService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;


@Controller
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @QueryMapping
    public List<Category> getAllCategories(){
        return categoryService.getAllCategories();
    }

    @QueryMapping
    public List<Category> getCategoriesByCategoryParentId(@Argument Long parentCategoryId){
        return categoryService.getAllCategoriesByParentCategoryId(parentCategoryId);

    }

    @MutationMapping
    public Category addCategory(@Argument String categoryName, @Argument Long parentCategoryId){
        Category transientCategory = Category.builder()
                .name(categoryName)
                .parentCategoryId(parentCategoryId)
                .build();
        return categoryService.saveCategory(transientCategory);
    }
}
