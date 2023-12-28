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
import org.example.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final CountryRepository countryRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public List<Product> getProductsByCategories(List<Category> categories) {
        return productRepository.findByCategoriesIn(categories);
    }

    @Override
    public List<Product> getProductsByBrand(Brand brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByBrands(List<Brand> brands) {
        return productRepository.findByBrandIn(brands);
    }

    @Override
    public Product addProduct(NewProduct product) {
        List<Category> categoryList = categoryRepository.findAllById(product.getCategoryIds());
        Brand brand = brandRepository.findById(product.getBrandId()).orElse(null);
        List<Country> countriesMadeInList = countryRepository.findAllById(product.getCountriesMadeInIds());
        Country countryTradeMark = countryRepository.findById(product.getCountryTradeMarkId()).orElse(null);
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
