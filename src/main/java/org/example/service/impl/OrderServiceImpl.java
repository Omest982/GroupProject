package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.DTO.NewOrder;
import org.example.DTO.NewOrderDetails;
import org.example.entity.*;
import org.example.entity.enums.OrderStatus;
import org.example.repository.OrderDetailsRepository;
import org.example.repository.OrderRepository;
import org.example.service.AddressService;
import org.example.service.OrderService;
import org.example.service.UserService;
import org.example.service.VariationDetailsService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderDetailsRepository orderDetailsRepository;
    private final VariationDetailsService variationDetailsService;
    private final AddressService addressService;
    private final UserService userService;


    @Override
    public Order addOrder(NewOrder order) {
        Address orderAddress = Address.builder()
                .region(order.getRegion())
                .city(order.getCity())
                .street(order.getStreet())
                .house(order.getHouse())
                .build();

        Address persistantAddress = getPersistentAddress(orderAddress);

        User user = userService.checkIfUserExistsByNewOrderShortInfo(order);

        Order transientOrder = Order.builder()
                .user(user)
                .paymentMethod(order.getPaymentMethod())
                .userComment(order.getUserComment())
                .orderStatus(OrderStatus.IN_PROGRESS)
                .address(persistantAddress)
                .orderDetails(new ArrayList<>())
                .build();

        return orderRepository.save(transientOrder);
    }

    private Address getPersistentAddress(Address transientAddress){
        Address persistantAddress = addressService.getAddressByAllFields(transientAddress);
        if (persistantAddress == null){
            return null;
        }

        return addressService.saveAddress(transientAddress);

    }

    @Override
    public Order addOrderDetails(NewOrderDetails orderDetails) {
        Order order = orderRepository.findById(orderDetails.getOrderId()).orElse(null);

        if (order == null){
            return null;
        }

        VariationDetails variationDetails = variationDetailsService.getVariationDetailsById(
                orderDetails.getVariationDetailsId()
        );

        if (variationDetails == null){
            return null;
        }

        BigDecimal sum = variationDetails.getPrice()
                .multiply(BigDecimal.valueOf(orderDetails.getQuantity()));

        BigDecimal sale = sum.multiply(variationDetails.getSale())
                .divide(BigDecimal.valueOf(100), 0, RoundingMode.HALF_DOWN);

        BigDecimal finalSum = sum.subtract(sale);

        OrderDetails transientOrderDetails = OrderDetails.builder()
                .order(order)
                .variationDetails(variationDetails)
                .quantity(orderDetails.getQuantity())
                .totalDetailPrice(finalSum)
                .build();

        orderDetailsRepository.save(transientOrderDetails);

        order.setSum(order.getSum().add(finalSum));

        return orderRepository.save(order);
    }

    @Override
    public List<Order> getAllOrdersByUserPhoneNumber(String userPhoneNumber) {
        User user = userService.getUserByPhoneNumber(userPhoneNumber);
        return getAllOrdersByUserId(user.getId());
    }

    @Override
    public List<Order> getAllOrdersByUserId(Long userId) {
        return orderRepository.findAllByUserId(userId);
    }


}
