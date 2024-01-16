package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.DTO.NewAddress;
import org.example.DTO.NewOrder;
import org.example.DTO.NewOrderDetails;
import org.example.entity.Address;
import org.example.entity.Order;
import org.example.service.OrderService;
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
    public List<Order> getAllOrdersByUserPhoneNumber(@Argument String userPhoneNumber){
        return orderService.getAllOrdersByUserPhoneNumber(userPhoneNumber);
    }

    @QueryMapping
    public List<Order> getAllOrdersByUserId(@Argument Long userId){
        return orderService.getAllOrdersByUserId(userId);
    }

    @MutationMapping
    public Order addOrder(@Argument NewAddress address, @Argument NewOrder orderInfo){
        return orderService.addOrder(address, orderInfo);
    }

    @MutationMapping
    public Order updateOrderAddress(@Argument Long orderId, @Argument NewAddress address){
        return orderService.updateOrderAddress(orderId, address);
    }

    @MutationMapping
    public Order addOrderDetails(@Argument NewOrderDetails orderDetails){
        return  orderService.addOrderDetails(orderDetails);
    }
}
