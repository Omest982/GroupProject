package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.DTO.NewVariationDetails;
import org.example.entity.ProductVariation;
import org.example.entity.VariationDetails;
import org.example.repository.VariationDetailsRepository;
import org.example.service.ProductVariationService;
import org.example.service.VariationDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VariationDetailsServiceImpl implements VariationDetailsService {
    private final VariationDetailsRepository variationDetailsRepository;
    private final ProductVariationService productVariationService;

    @Override
    public List<VariationDetails> getAllVariationDetails() {
        return variationDetailsRepository.findAll();
    }

    @Override
    public List<VariationDetails> getAllVariationDetailsByProductVariationId(Long productVariationId) {
        return variationDetailsRepository.findAllByProductVariationId(productVariationId);
    }

    @Override
    public VariationDetails getVariationDetailsById(Long variationDetailsId) {
        return variationDetailsRepository.findById(variationDetailsId).orElse(null);
    }

    @Override
    public VariationDetails addVariationDetails(NewVariationDetails variationDetails) {
        ProductVariation productVariation = productVariationService.getProductVariationById(
                variationDetails.getProductVariationId()
        );

        VariationDetails transientVariationDetails = VariationDetails.builder()
                .price(variationDetails.getPrice())
                .sale(variationDetails.getSale())
                .shippingFrom(variationDetails.getShippingFrom())
                .productVariation(productVariation)
                .build();

        return variationDetailsRepository.save(transientVariationDetails);
    }

    @Override
    public VariationDetails updateVariationDetails(Long variationDetailsId, NewVariationDetails updatedVariationDetails) {
        ProductVariation productVariation = productVariationService.getProductVariationById(
                updatedVariationDetails.getProductVariationId()
        );

        VariationDetails variationDetails = getVariationDetailsById(variationDetailsId);

        variationDetails.setProductVariation(productVariation);
        variationDetails.setSale(updatedVariationDetails.getSale());
        variationDetails.setPrice(updatedVariationDetails.getPrice());
        variationDetails.setShippingFrom(updatedVariationDetails.getShippingFrom());

        return variationDetailsRepository.save(variationDetails);
    }

    @Override
    public String deleteVariationDetails(Long variationDetailsId) {
        variationDetailsRepository.deleteById(variationDetailsId);
        if (getVariationDetailsById(variationDetailsId) == null){
            return "Successfully deleted variation details!";
        }
        return "Failed to delete variation details!";
    }
}
