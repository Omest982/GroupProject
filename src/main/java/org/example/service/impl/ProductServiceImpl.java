package org.example.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.DTO.NewProduct;
import org.example.DTO.PageRequestDTO;
import org.example.entity.*;
import org.example.repository.ProductRepository;
import org.example.service.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
    public PageImpl<Product> getAllProductsPaged(PageRequestDTO pageRequestDTO) {
        Page<Product> productPage = productRepository.findAll(pageRequestDTO.getPageRequest());
        return new PageImpl<>(productPage.stream().toList(), productPage.getPageable(), productPage.getTotalElements());
    }

    @Override
    public Product getProductById(Long productId) {
        return productRepository.findById(productId).orElseThrow(()
        -> new EntityNotFoundException(String.format("Product with id %s not found!", productId)));
    }

    @Override
    public PageImpl<Product> getAllProductsByCategoryIdsPaged(Iterable<Long> categoryIds, PageRequestDTO pageRequestDTO) {
        List<Category> categoryList = categoryService.getAllCategoriesByIds(categoryIds);
        Page<Product> productPage = productRepository.findAllByCategoriesIn(categoryList, pageRequestDTO.getPageRequest());
        return new PageImpl<>(productPage.stream().toList(), productPage.getPageable(), productPage.getTotalElements());
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    private List<Long> getAllParentCategoryIdsByCategoryId(Long categoryId) {
        Category category = categoryService.getCategoryById(categoryId);

        if (category.getParentCategoryId() == null){
            return List.of(category.getId());
        }

        List<Long> categoryIdsList = new ArrayList<>();

        while (category.getParentCategoryId() != null){
            categoryIdsList.add(category.getId());
            category = categoryService.getCategoryById(category.getParentCategoryId());
        }

        categoryIdsList.add(category.getId());

        return categoryIdsList;
    }

    @Transactional
    @Override
    public Product updateProduct(Long productId, NewProduct updatedProduct) {

        Product product = getProductById(productId);

        if (product == null){
            String msg ="Product with id " + productId + " was not found!";
            throw new EntityNotFoundException(msg);
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
    public Boolean deleteProduct(Long productId) {
        productRepository.deleteById(productId);
        return true;
    }

    @Override
    public PageImpl<Product> searchProductsPaged(String searchString ,PageRequestDTO pageRequestDTO) {

        if (searchString.length() <= 1){
            return null;
        }

        Set<Product> answer = new TreeSet<>();

        String filteredString = searchString.replaceAll("\\pP", "");

        List<String> stringList = List.of(filteredString.split(" "));

        for (String str: stringList){

            if (str.length() <= 1){
                continue;
            }

            answer.addAll(productRepository.findAllByParam(str));
        }

        List<Long> productIds = new ArrayList<>();

        for (Product product: answer){
            productIds.add(product.getId());
        }

        Page<Product> productPage = productRepository.findAllByIdIn(productIds, pageRequestDTO.getPageRequest());

        return new PageImpl<>(productPage.stream().toList(), productPage.getPageable(), productPage.getTotalElements());
    }

    private void initProductWithIds(Product product ,NewProduct newProduct){
        List<Long> categoryIdsList = getAllParentCategoryIdsByCategoryId(newProduct.getCategoryId());
        List<Category> categoryList = categoryService.getAllCategoriesByIds(categoryIdsList);
        if (categoryList.isEmpty()){
            String msg = "Creating product with no categories!";
            throw new EntityNotFoundException(msg);
        }

        List<Country> countriesMadeInList = countryService.getAllCountriesByIds(newProduct.getCountriesMadeInIds());
        if (countriesMadeInList.isEmpty()){
            String msg = "Creating product with no countries made in!";
            throw new EntityNotFoundException(msg);
        }

        List<Image> images = imageService.addOrGetImages(newProduct.getImageLinks());
        if (images.isEmpty()){
            String msg = "Creating product with no images!";
            throw new EntityNotFoundException(msg);
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

        Image image = Image.builder()
                .imageLink(imageLink)
                .build();

        product.getImages().add(image);
        return productRepository.save(product);
    }
}
