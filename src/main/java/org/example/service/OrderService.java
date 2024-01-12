package org.example.service;

import org.example.DTO.NewAddress;
import org.example.DTO.NewOrder;
import org.example.DTO.NewOrderDetails;
import org.example.entity.Address;
import org.example.entity.Order;

import java.util.List;

public interface OrderService {
    Order addOrder(NewOrder order);

    Order addOrderDetails(NewOrderDetails orderDetails);

    List<Order> getAllOrdersByUserPhoneNumber(String userPhoneNumber);

    List<Order> getAllOrdersByUserId(Long userId);

    Order updateOrderAddress(Long orderId, NewAddress address);
    Order getOrderById(Long orderId);
}
