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

    @QueryMapping
    public List<Category> getAllHeadCategories(){
        return categoryService.getAllHeadCategories();
    }

    @MutationMapping
    public Category addCategory(@Argument String categoryName, @Argument Long parentCategoryId){
        return categoryService.addCategory(categoryName, parentCategoryId);
    }

    @MutationMapping
    public Category updateParentCategory(@Argument Long categoryId, @Argument Long parentCategoryId){
        return categoryService.updateParentCategory(categoryId, parentCategoryId);
    }

    @MutationMapping
    public String deleteCategory(@Argument Long categoryId){
        return categoryService.deleteCategory(categoryId);
    }
}
