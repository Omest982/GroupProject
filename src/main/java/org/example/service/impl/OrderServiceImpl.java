package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.DTO.NewOrder;
import org.example.entity.Address;
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
        Address orderAddress = Address.builder()
                .region(order.getRegion())
                .city(order.getCity())
                .street(order.getStreet())
                .house(order.getHouse())
                .build();

        Address persistantAddress = getPersistentAddress(orderAddress);

        Order transientOrder = Order.builder()
                .build();
        return null;
    }

    private Address getPersistentAddress(Address transientAddress){
        return null;

    }

    @Override
    public Order addOrderDetails(Long orderId, Long variationDetailsId, int quantity) {
        return null;
    }


}
