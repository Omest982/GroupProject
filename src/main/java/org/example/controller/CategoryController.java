package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.entity.Category;
import org.example.service.CategoryService;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@Controller
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @QueryMapping
    public List<Category> getAllCategories(){
        return categoryService.getAllCategories();
    }
}
