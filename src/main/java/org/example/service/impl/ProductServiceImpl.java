package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.DTO.NewProduct;
import org.example.entity.*;
import org.example.entity.enums.ProductStatus;
import org.example.exception.EntityNotFoundException;
import org.example.repository.ProductRepository;
import org.example.service.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@RequiredArgsConstructor
@Slf4j
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

        Product product = getProductById(productId);

        if (product == null){
            log.error("Product not found!");
            throw new EntityNotFoundException("Product with id " + productId + " was not found!");
        }

        initProductWithIds(product, updatedProduct);

        product.setName(updatedProduct.getName());
        product.setProductGroup(updatedProduct.getProductGroup());
        product.setProductStatus(updatedProduct.getProductStatus());
        product.setSex(updatedProduct.getSex());
        product.setClassification(updatedProduct.getClassification());
        product.setAdditionalInfo(updatedProduct.getAdditionalInfo());
        product.setDescription(updatedProduct.getDescription());

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

    private void initProductWithIds(Product product ,NewProduct newProduct){
        List<Category> categoryList = categoryService.getAllCategoriesByIds(newProduct.getCategoryIds());
        if (categoryList.size() == 0){
            log.error("Creating product with no categories!");
            throw new EntityNotFoundException("No categories were added!");
        }

        List<Country> countriesMadeInList = countryService.getAllCountriesByIds(newProduct.getCountriesMadeInIds());
        if (countriesMadeInList.size() == 0){
            log.error("Creating product with no countries made in!");
            throw new EntityNotFoundException("No countries made in were added!");
        }

        List<Image> images = imageService.addOrGetImages(newProduct.getImageLinks());
        if (images.size() == 0){
            log.error("Creating product with no images!");
            throw new EntityNotFoundException("No images were added!");
        }

        Country countryTradeMark = countryService.getCountryById(newProduct.getCountryTradeMarkId());
        Brand brand = brandService.getBrandById(newProduct.getBrandId());

        product.setCategories(new HashSet<>(categoryList));
        product.setCountriesMadeIn(new HashSet<>(countriesMadeInList));
        product.setImages(new HashSet<>(images));
        product.setCountryTradeMark(countryTradeMark);
        product.setBrand(brand);
    }

    @Transactional
    @Override
    public Product addProduct(NewProduct product) {

        Product transientProduct = Product.builder()
                .name(product.getName())
                .productGroup(product.getProductGroup())
                .productStatus(product.getProductStatus())
                .sex(product.getSex())
                .classification(product.getClassification())
                .additionalInfo(product.getAdditionalInfo())
                .description(product.getDescription())
                .build();

        initProductWithIds(transientProduct, product);

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
