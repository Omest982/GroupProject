package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.entity.Brand;
import org.example.service.BrandService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;


@RequiredArgsConstructor
@Controller
public class BrandController {
    private final BrandService brandService;

    @MutationMapping
    public Brand addBrand(@Argument String brandName){
        return brandService.addBrand(brandName);
    }

    @QueryMapping
    public Brand getBrandById(@Argument Long id){
        return brandService.getBrandById(id);
    }

    @QueryMapping
    public List<Brand> getAllBrands(){
        return brandService.getAllBrands();
    }
}
