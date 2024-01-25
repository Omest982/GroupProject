package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.DTO.NewShippingInfo;
import org.example.DTO.NewOrder;
import org.example.DTO.NewOrderDetails;
import org.example.DTO.PageRequestDTO;
import org.example.entity.*;
import org.example.entity.enums.OrderStatus;
import org.example.exception.UserNotFoundException;
import org.example.repository.OrderDetailsRepository;
import org.example.repository.OrderRepository;
import org.example.service.ShippingInfoService;
import org.example.service.OrderService;
import org.example.service.UserService;
import org.example.service.VariationDetailsService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderDetailsRepository orderDetailsRepository;
    private final VariationDetailsService variationDetailsService;
    private final ShippingInfoService shippingInfoService;
    private final UserService userService;


    @Transactional
    @Override
    public Order addOrder(NewShippingInfo newShippingInfo, NewOrder newOrder, Long userId) {

        ShippingInfo persistantShippingInfo = shippingInfoService.addOrGetShippingInfo(newShippingInfo);

        User user = null;

        if (userId != null){
            user = userService.getUserById(userId);
        }

        if (user != null){
            if(!user.getShippingInfos().contains(persistantShippingInfo)){
                user.getShippingInfos().add(persistantShippingInfo);
                userService.saveUser(user);
            }
        }

        Order transientOrder = Order.builder()
                .user(user)
                .paymentMethod(newOrder.getPaymentMethod())
                .userComment(newOrder.getUserComment())
                .orderStatus(OrderStatus.IN_PROGRESS)
                .shippingInfo(persistantShippingInfo)
                .sum(BigDecimal.valueOf(0))
                .build();

        return orderRepository.save(transientOrder);
    }

    @Transactional
    @Override
    public Order addOrderDetails(NewOrderDetails newOrderDetails) {
        Order order = getOrderById(newOrderDetails.getOrderId());

        VariationDetails variationDetails = variationDetailsService.getVariationDetailsById(
                newOrderDetails.getVariationDetailsId()
        );

        BigDecimal finalSum = getOrderDetailsSum(variationDetails, newOrderDetails.getQuantity());

        OrderDetails transientOrderDetails = OrderDetails.builder()
                .order(order)
                .variationDetails(variationDetails)
                .quantity(newOrderDetails.getQuantity())
                .totalDetailPrice(finalSum)
                .build();

        orderDetailsRepository.save(transientOrderDetails);

        order.setSum(order.getSum().add(finalSum));

        return orderRepository.save(order);
    }

    private BigDecimal getOrderDetailsSum(VariationDetails variationDetails,
                                   Integer quantity){

        BigDecimal sum = variationDetails.getPrice()
                .multiply(BigDecimal.valueOf(quantity));

        BigDecimal sale = sum.multiply(variationDetails.getSale())
                .divide(BigDecimal.valueOf(100), 0, RoundingMode.HALF_DOWN);

        return sum.subtract(sale);
    }

    @Override
    public Page<Order> getAllOrdersByUserIdPaged(Long userId, PageRequestDTO pageRequestDTO) {
        return orderRepository.findAllByUserId(userId, pageRequestDTO.getPageRequest());
    }

    @Transactional
    @Override
    public Order updateOrderAddress(Long orderId, NewShippingInfo newShippingInfo) {
        Order order = getOrderById(orderId);

        ShippingInfo persistantShippingInfo = shippingInfoService.addOrGetShippingInfo(newShippingInfo);

        order.setShippingInfo(persistantShippingInfo);

        return orderRepository.save(order);
    }

    @Override
    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }
}
