package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.DTO.NewProduct;
import org.example.DTO.PageRequestDTO;
import org.example.entity.*;
import org.example.entity.enums.ProductStatus;
import org.example.exception.EntityNotFoundException;
import org.example.repository.ProductRepository;
import org.example.service.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public Page<Product> getAllProducts(PageRequestDTO pageRequestDTO) {
        return productRepository.findAll(pageRequestDTO.getPageRequest());
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

    @Override
    public Page<Product> getAllProductsByCategoryIdsPaged(Iterable<Long> categoryIds, PageRequestDTO pageRequestDTO) {
        List<Category> categoryList = categoryService.getAllCategoriesByIds(categoryIds);
        return productRepository.findAllByCategoriesIn(categoryList, pageRequestDTO.getPageRequest());
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
    public Page<Product> searchProductsPaged(String searchString ,PageRequestDTO pageRequestDTO) {

        if (searchString.length() <= 1){
            return null;
        }

        Set<Product> answer = new HashSet<>();

        String filteredString = searchString.replaceAll("\\pP", "");

        List<String> stringList = List.of(filteredString.split(" "));

        for (String str: stringList){

            if (str.length() <= 1){
                continue;
            }

            answer.addAll(productRepository.findAllByParams(str));
        }

        List<Product> answerList = new ArrayList<>(answer);
        //Forming page answer
        int start = pageRequestDTO.getPageNumber();
        int end = Math.min((start + pageRequestDTO.getSizePerPage()), answer.size());
        return new PageImpl<>(answerList.subList(start, end), pageRequestDTO.getPageRequest(), answer.size());
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
