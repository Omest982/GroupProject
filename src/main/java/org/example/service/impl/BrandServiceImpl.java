package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.entity.Brand;
import org.example.repository.BrandRepository;
import org.example.service.BrandService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;

    @Override
    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }

    @Override
    public Brand getBrandById(Long id) {
        return brandRepository.findById(id).orElse(null);
    }

    @Override
    public Brand getBrandByName(String name) {
        return brandRepository.findByName(name);
    }

    @Transactional
    @Override
    public Brand addBrand(String name) {
        Brand transientBrand = Brand.builder()
                .name(name)
                .build();
        return brandRepository.save(transientBrand);
    }

    @Transactional
    @Override
    public String deleteBrandById(Long brandId) {
        brandRepository.deleteById(brandId);
        return "Successfully deleted brand!";
    }
}
