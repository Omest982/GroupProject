package org.example.service;

import org.example.DTO.NewOrder;
import org.example.entity.Order;

public interface OrderService {
    Order addOrder(NewOrder order);
}
