package org.example.service;

import org.example.entity.Brand;

import java.util.List;

public interface BrandService {
    List<Brand> getAllBrands();
    Brand getBrandById(Long id);
    Brand addBrand(String brandName);
    Boolean deleteBrandById(Long brandId);
}
