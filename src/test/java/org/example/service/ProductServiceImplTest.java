package org.example.service;

import org.example.DTO.NewProduct;
import org.example.entity.Category;
import org.example.entity.Country;
import org.example.entity.Image;
import org.example.entity.enums.Classification;
import org.example.entity.enums.ProductStatus;
import org.example.entity.enums.Sex;
import org.example.service.impl.ProductServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Set;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

//    @Mock
//    private CategoryService categoryService;
//
//    @Mock
//    private CountryService countryService;
//
//    @Mock
//    private ImageService imageService;
//
//    @Mock BrandService brandService;
//
//    @InjectMocks
//    private ProductServiceImpl productService;
//    @Before
//    public void setUp() {
//        MockitoAnnotations.initMocks(this);
//    }
//
//    @Test
//    public void addProduct(){
//
//        NewProduct mockProduct = NewProduct.builder()
//                .name("name")
//                .productGroup("group")
//                .productStatus(ProductStatus.AVAILABLE)
//                .sex(Sex.MALE)
//                .classification(Classification.ELITE)
//                .additionalInfo(null)
//                .description("description")
//                .categoryId(1L)
//                .brandId(1L)
//                .imageLinks(Set.of("Fake!"))
//                .countryTradeMarkId(1L)
//                .countriesMadeInIds(List.of(1L))
//                .build();
//
//        Category mockCategory = Category.builder()
//                .id(1L)
//                .parentCategoryId(null)
//                .name("category1")
//                .build();
//
//        Country mockCountry = Country.builder()
//                .id(1L)
//                .name("Ukraine")
//                .build();
//
//        Image mockImage = Image.builder()
//                .id(1L)
//                .imageLink("Mock!")
//                .build();
//
//        Mockito.when(categoryService.getCategoryById(1L))
//                        .thenReturn(mockCategory);
//
//        Mockito.when(categoryService.getAllCategoriesByIds(List.of(mockCategory.getId())))
//                        .thenReturn(List.of(mockCategory));
//
//        Mockito.when(countryService.getAllCountriesByIds(List.of(mockCountry.getId())))
//                        .thenReturn(List.of(mockCountry));
//
//        //Mockito.when(countryService.getCountryById())
//
//        Mockito.when(imageService.addOrGetImages(mockProduct.getImageLinks()))
//                        .thenReturn(List.of(mockImage));
//
//        productService.addProduct(mockProduct);
//    }
}
