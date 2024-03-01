package org.example.service;

import org.example.DTO.NewProduct;
import org.example.entity.Category;
import org.example.entity.enums.Classification;
import org.example.entity.enums.ProductStatus;
import org.example.entity.enums.Sex;
import org.example.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    public void addProduct(){

        NewProduct newProduct = NewProduct.builder()
                .name("name")
                .productGroup("group")
                .productStatus(ProductStatus.AVAILABLE)
                .sex(Sex.MALE)
                .classification(Classification.ELITE)
                .additionalInfo(null)
                .description("description")
                .categoryId(1L)
                .build();

//        Mockito.when(categoryService.getCategoryById(1L))
//                        .thenReturn(Category.builder()
//                                .id(1L)
//                                .parentCategoryId(null)
//                                .name("category1")
//                                .build());

        productService.addProduct(newProduct);
    }
}
