package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.DTO.NewOrder;
import org.example.DTO.NewOrderDetails;
import org.example.DTO.NewShippingInfo;
import org.example.DTO.PageRequestDTO;
import org.example.DTO.mailMessages.OrderAcceptedMail;
import org.example.entity.*;
import org.example.entity.enums.OrderStatus;
import org.example.repository.OrderDetailsRepository;
import org.example.repository.OrderRepository;
import org.example.service.*;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final ShippingInfoService shippingInfoService;
    private final UserService userService;
    private final MailSenderService mailSenderService;


    @Transactional
    @Override
    public Order addOrder(NewShippingInfo newShippingInfo,
                          NewOrder newOrder,
                          Long userId,
                          List<NewOrderDetails> orderDetailsInfo) {

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

        Order finalOrder = addAllOrderDetailsAndSave(orderDetailsInfo, transientOrder);

//        if (user != null){
//            sendOrderConfirmedMessage(finalOrder.getId(), user.getEmail());
//        }

        return finalOrder;
    }

    private void sendOrderConfirmedMessage(Long orderId, String userEmail) {
        OrderAcceptedMail orderAcceptedMail = OrderAcceptedMail.builder()
                .orderId(orderId)
                .emailTo(userEmail)
                .build();
        mailSenderService.sendOrderAcceptedMail(orderAcceptedMail);
    }

    @Transactional
    private Order addAllOrderDetailsAndSave(List<NewOrderDetails> newOrderDetails, Order order) {

        List<OrderDetails> transientOrderDetailsList = new ArrayList<>();

        for (NewOrderDetails newOrderDetailsEntity: newOrderDetails){

            VariationDetails variationDetails = variationDetailsService.getVariationDetailsById(
                    newOrderDetailsEntity.getVariationDetailsId()
            );

            BigDecimal finalSum = getOrderDetailsSum(variationDetails, newOrderDetailsEntity.getQuantity());

            OrderDetails transientOrderDetails = OrderDetails.builder()
                    .order(order)
                    .variationDetails(variationDetails)
                    .quantity(newOrderDetailsEntity.getQuantity())
                    .totalDetailPrice(finalSum)
                    .build();

            transientOrderDetailsList.add(transientOrderDetails);
        }

        order.setSum(getOrderDetailsListSum(transientOrderDetailsList));
        order.setOrderDetails(transientOrderDetailsList);

        return orderRepository.save(order);
    }

    private BigDecimal getOrderDetailsListSum(List<OrderDetails> orderDetailsList){

        BigDecimal sum = BigDecimal.valueOf(0);

        for (OrderDetails orderDetails: orderDetailsList){
            sum = sum.add(orderDetails.getTotalDetailPrice());
        }

        return sum;
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
