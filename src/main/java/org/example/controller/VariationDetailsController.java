package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.DTO.NewVariationDetails;
import org.example.entity.VariationDetails;
import org.example.service.VariationDetailsService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class VariationDetailsController {
    private final VariationDetailsService variationDetailsService;

    @QueryMapping
    public List<VariationDetails> getVariationDetailsByProductVariationId(@Argument Long productVariationId){
        return variationDetailsService.getAllVariationDetailsByProductVariationId(productVariationId);
    }

    @MutationMapping
    public VariationDetails addVariationDetails(@Argument NewVariationDetails variationDetails){
        return variationDetailsService.addVariationDetails(variationDetails);
    }

}
