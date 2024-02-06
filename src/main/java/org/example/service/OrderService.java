package org.example.service;

import org.example.DTO.NewShippingInfo;
import org.example.DTO.NewOrder;
import org.example.DTO.NewOrderDetails;
import org.example.DTO.PageRequestDTO;
import org.example.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {
    Order addOrder(NewShippingInfo newShippingInfo,
                   NewOrder orderInfo,
                   Long userId,
                   List<NewOrderDetails> orderDetailsInfo);

    PageImpl<Order> getAllOrdersByUserIdPaged(Long userId, PageRequestDTO pageRequestDTO);

    Order updateOrderAddress(Long orderId, NewShippingInfo address);
    Order getOrderById(Long orderId);

    Order saveOrder(Order order);
}
