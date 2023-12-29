package org.example.service;

import org.example.DTO.NewBasketDetails;
import org.example.entity.Basket;

public interface BasketService {
    Basket getBasketByUserId(Long userId);

    Basket addBasketDetails(NewBasketDetails basketDetails);

    Basket addBasket();

}
