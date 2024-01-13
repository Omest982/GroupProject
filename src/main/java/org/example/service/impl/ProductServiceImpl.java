package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.DTO.NewProduct;
import org.example.entity.*;
import org.example.repository.ProductRepository;
import org.example.service.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
        productRepository.findAll();
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public List<Product> getAllProductsByCategoryIds(Iterable<Long> categoryIds) {
        List<Category> categoryList = categoryService.getAllCategoriesByIds(categoryIds);
        return productRepository.findAllByCategoriesIn(categoryList);
    }

    @Transactional
    @Override
    public Product updateProduct(Long productId, NewProduct updatedProduct) {
        List<Category> categoryList = categoryService.getAllCategoriesByIds(updatedProduct.getCategoryIds());
        Brand brand = brandService.getBrandById(updatedProduct.getBrandId());
        List<Country> countriesMadeInList = countryService.getAllCountriesByIds(updatedProduct.getCountriesMadeInIds());
        Country countryTradeMark = countryService.getCountryById(updatedProduct.getCountryTradeMarkId());
        List<Image> images = imageService.addOrGetImages(updatedProduct.getImageLinks());

        //TODO: Добавить проверки если null

        Product product = getProductById(productId);

        product.setName(updatedProduct.getName());
        product.setImages(new HashSet<>(images));
        product.setCategories(new HashSet<>(categoryList));
        product.setBrand(brand);
        product.setProductGroup(updatedProduct.getProductGroup());
        product.setSex(updatedProduct.getSex());
        product.setClassification(updatedProduct.getClassification());
        product.setAdditionalInfo(updatedProduct.getAdditionalInfo());
        product.setDescription(updatedProduct.getDescription());
        product.setCountriesMadeIn(new HashSet<>(countriesMadeInList));
        product.setCountryTradeMark(countryTradeMark);

        return productRepository.save(product);
    }

    @Transactional
    @Override
    public String deleteProduct(Long productId) {
        productRepository.deleteById(productId);
        return "Successfully deleted product!";
    }

    @Override
    public List<Product> getAllProductsByBrand(Brand brand) {
        return productRepository.findAllByBrand(brand);
    }

    @Override
    public List<Product> getAllProductsByBrands(Iterable<Brand> brands) {
        return productRepository.findAllByBrandIn(brands);
    }

    @Transactional
    @Override
    public Product addProduct(NewProduct product) {
        List<Category> categoryList = categoryService.getAllCategoriesByIds(product.getCategoryIds());
        Brand brand = brandService.getBrandById(product.getBrandId());
        List<Country> countriesMadeInList = countryService.getAllCountriesByIds(product.getCountriesMadeInIds());
        Country countryTradeMark = countryService.getCountryById(product.getCountryTradeMarkId());
        List<Image> images = imageService.addOrGetImages(product.getImageLinks());

        //TODO: Добавить проверки если null

        Product transientProduct = Product.builder()
                .name(product.getName())
                .images(new HashSet<>(images))
                .categories(new HashSet<>(categoryList))
                .brand(brand)
                .productGroup(product.getProductGroup())
                .sex(product.getSex())
                .classification(product.getClassification())
                .additionalInfo(product.getAdditionalInfo())
                .description(product.getDescription())
                .countriesMadeIn(new HashSet<>(countriesMadeInList))
                .countryTradeMark(countryTradeMark)
                .build();

        return productRepository.save(transientProduct);
    }

    @Transactional
    @Override
    public Product addImageToProduct(Long productId, String imageLink) {
        Product product = getProductById(productId);
        Image image = imageService.addImage(imageLink);
        product.getImages().add(image);
        return productRepository.save(product);
    }
}
