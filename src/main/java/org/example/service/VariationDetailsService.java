package org.example.service;

import org.example.DTO.NewVariationDetails;
import org.example.entity.VariationDetails;

import java.util.List;

public interface VariationDetailsService {
    List<VariationDetails> getAllVariationDetails();
    List<VariationDetails> getAllVariationDetailsByProductVariationId(Long productVariationId);
    VariationDetails getVariationDetailsById(Long variationDetailsId);
    VariationDetails addVariationDetails(NewVariationDetails variationDetails);

    VariationDetails updateVariationDetails(Long variationDetailsId, NewVariationDetails updatedVariationDetails);

    String deleteVariationDetails(Long variationDetailsId);
}
