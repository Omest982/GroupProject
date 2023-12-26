package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.entity.Category;
import org.example.entity.User;
import org.example.repository.CategoryRepository;
import org.example.repository.UserRepository;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@Controller
@CrossOrigin(origins = {"http://13.51.170.112:8080", "http://localhost:3000"}, allowCredentials = "true")
@RequiredArgsConstructor
public class GraphQLController{
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    @QueryMapping
    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }

    @QueryMapping
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

}
