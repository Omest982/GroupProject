package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.DTO.NewShippingInfo;
import org.example.DTO.NewOrder;
import org.example.DTO.NewOrderDetails;
import org.example.DTO.PageRequestDTO;
import org.example.entity.Order;
import org.example.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @QueryMapping
    public Page<Order> getAllOrdersByUserIdPaged(@Argument Long userId, @Argument PageRequestDTO pageRequestDTO){
        return orderService.getAllOrdersByUserIdPaged(userId, pageRequestDTO);
    }

    @MutationMapping
    public Order addOrder(@Argument NewShippingInfo newShippingInfo,
                          @Argument NewOrder orderInfo,
                          @Argument Long userId,
                          @Argument List<NewOrderDetails> orderDetailsInfo){
        return orderService.addOrder(newShippingInfo, orderInfo, userId, orderDetailsInfo);
    }

    @MutationMapping
    public Order updateOrderShippingInfo(@Argument Long orderId, @Argument NewShippingInfo newShippingInfo){
        return orderService.updateOrderAddress(orderId, newShippingInfo);
    }

//    @MutationMapping
//    public Order addOrderDetails(@Argument NewOrderDetails orderDetails){
//        return  orderService.addOrderDetails(orderDetails);
//    }
}
