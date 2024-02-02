package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.DTO.PageRequestDTO;
import org.example.entity.Product;
import org.example.service.ProductService;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class ProductRESTController {

    private final ProductService productService;

    @PostMapping
    public PageImpl<Product> searchProductsPaged(@RequestParam(name = "q") String searchString,
                                                 @RequestBody PageRequestDTO pageRequestDTO){
        System.out.println(pageRequestDTO.getPageNumber());
        return productService.searchProductsPaged(searchString, pageRequestDTO);
    }
}
