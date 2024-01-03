package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.DTO.NewOrder;
import org.example.entity.Order;
import org.example.repository.OrderDetailsRepository;
import org.example.repository.OrderRepository;
import org.example.service.OrderService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderDetailsRepository orderDetailsRepository;


    @Override
    public Order addOrder(NewOrder order) {
        return null;
    }
}
