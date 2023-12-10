package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.entity.Brand;
import org.example.repository.BrandRepository;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class GraphQLController{
    private final BrandRepository brandRepository;

    @QueryMapping
    public Brand getBrandById(@Argument Long id){
        return brandRepository.findById(id).orElse(null);
    }

    @QueryMapping
    public List<Brand> getAllBrands(){
        return brandRepository.findAll();
    }

}
