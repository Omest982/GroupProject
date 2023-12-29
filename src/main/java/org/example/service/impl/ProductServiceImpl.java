package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.DTO.NewProduct;
import org.example.entity.Brand;
import org.example.entity.Category;
import org.example.entity.Country;
import org.example.entity.Product;
import org.example.repository.BrandRepository;
import org.example.repository.CategoryRepository;
import org.example.repository.CountryRepository;
import org.example.repository.ProductRepository;
import org.example.service.BrandService;
import org.example.service.CategoryService;
import org.example.service.CountryService;
import org.example.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final BrandService brandService;
    private final CountryService countryService;

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
        Product transientProduct = Product.builder()
                .name(product.getName())
                .categories(categoryList)
                .brand(brand)
                .sex(product.getSex())
                .classification(product.getClassification())
                .isLiquid(product.isLiquid())
                .additionalInfo(product.getAdditionalInfo())
                .countriesMadeIn(countriesMadeInList)
                .countryTradeMark(countryTradeMark)
                .build();
        return productRepository.save(transientProduct);
    }


}
