package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.DTO.NewBasketDetails;
import org.example.entity.Basket;
import org.example.entity.BasketDetails;
import org.example.entity.VariationDetails;
import org.example.repository.BasketDetailsRepository;
import org.example.repository.BasketRepository;
import org.example.service.BasketService;
import org.example.service.VariationDetailsService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class BasketServiceImpl implements BasketService {
    private final BasketRepository basketRepository;
    private final BasketDetailsRepository basketDetailsRepository;
    private final VariationDetailsService variationDetailsService;
    @Override
    public Basket getBasketByUserId(Long userId) {
        return basketRepository.findByUserId(userId);
    }

    @Override
    public Basket addBasketDetails(NewBasketDetails basketDetails) {
        VariationDetails variationDetails = variationDetailsService.getVariationDetailsById(
                basketDetails.getVariationDetailsId()
        );

        BigDecimal totalPrice = variationDetails.getPrice()
                .multiply(BigDecimal.valueOf(basketDetails.getQuantity()));

        BasketDetails transientBasketDetails = BasketDetails.builder()
                .quantity(basketDetails.getQuantity())
                .variationDetails(variationDetails)
                .totalDetailPrice(totalPrice)
                .build();

        Basket basket = basketRepository.findById(basketDetails.getBasketId()).orElse(null);

        return null;
    }

    @Override
    public Basket addBasket() {
        return null;
    }
}
