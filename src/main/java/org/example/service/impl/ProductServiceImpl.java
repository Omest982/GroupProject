package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.DTO.NewProduct;
import org.example.entity.*;
import org.example.repository.ProductRepository;
import org.example.service.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final BrandService brandService;
    private final CountryService countryService;
    private final ImageService imageService;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public List<Product> getAllProductsByCategories(List<Category> categories) {
        return productRepository.findByCategoriesIn(categories);
    }

    @Override
    public List<Product> getAllProductsByBrand(Brand brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getAllProductsByBrands(List<Brand> brands) {
        return productRepository.findByBrandIn(brands);
    }

    @Override
    public Product addProduct(NewProduct product) {
        List<Category> categoryList = categoryService.getAllCategoriesByIds(product.getCategoryIds());
        Brand brand = brandService.getBrandById(product.getBrandId());
        List<Country> countriesMadeInList = countryService.getAllCountriesByIds(product.getCountriesMadeInIds());
        Country countryTradeMark = countryService.getCountryById(product.getCountryTradeMarkId());
        List<Image> images = imageService.addImages(product.getImageLinks());

        System.out.println(product.getProductGroup());

        Product transientProduct = Product.builder()
                .name(product.getName())
                .images(images)
                .categories(categoryList)
                .brand(brand)
                .productGroup(product.getProductGroup())
                .sex(product.getSex())
                .classification(product.getClassification())
                .isLiquid(product.isLiquid())
                .additionalInfo(product.getAdditionalInfo())
                .description(product.getDescription())
                .countriesMadeIn(countriesMadeInList)
                .countryTradeMark(countryTradeMark)
                .productVariations(new ArrayList<>())
                .build();

        return productRepository.save(transientProduct);
    }

    @Override
    public Product addImageToProduct(Long productId, String imageLink) {
        Product product = getProductById(productId);
        Image image = imageService.addImage(imageLink);
        product.getImages().add(image);
        return productRepository.save(product);
    }


}
