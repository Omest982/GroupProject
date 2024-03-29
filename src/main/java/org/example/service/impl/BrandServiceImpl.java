package org.example.service.impl;

import jakarta.persistence.EntityNotFoundException;
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
    public Boolean deleteBrandById(Long brandId) {

        if (getBrandById(brandId) == null){
            throw new EntityNotFoundException("brand with id " + brandId + " doesn't exist!");
        }

        brandRepository.deleteById(brandId);
        return true;
    }
}
