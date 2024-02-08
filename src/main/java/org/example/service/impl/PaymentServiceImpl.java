package org.example.service.impl;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.entity.Order;
import org.example.entity.OrderDetails;
import org.example.entity.enums.OrderStatus;
import org.example.entity.enums.PaymentMethod;
import org.example.exception.OrderException;
import org.example.service.OrderService;
import org.example.service.PaymentService;
import org.example.utils.CryptoTool;
import org.example.utils.UrlTool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final UrlTool urlTool;
    @Value("${stripe.api-key}")
    private String stripeApiKey;
    private final OrderService orderService;
    private final CryptoTool cryptoTool;
    private static final String CURRENCY = "usd";
    @Override
    public Session createCheckoutSession(HttpServletRequest request, Long orderId) throws StripeException{
        Stripe.apiKey = stripeApiKey;

        String urlPrefix = urlTool.getFullUrlPrefix(request);

        List<SessionCreateParams.LineItem> lineItemList = fullLineItems(orderId);

        String orderIdHashed = cryptoTool.hashOf(orderId);

        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(urlPrefix + "/payment/success?orderId=" + orderIdHashed)
                .setCancelUrl(urlPrefix + "/payment/cancel")
                .addAllLineItem(lineItemList)
                .build();

        return Session.create(params);
    }

    @Transactional
    @Override
    public String paymentSuccess(String orderIdHashed) {
        Long orderId = cryptoTool.idOf(orderIdHashed);

        Order order = orderService.getOrderById(orderId);

        orderCheck(order);

        order.setOrderStatus(OrderStatus.IN_PROGRESS);

        orderService.saveOrder(order);

        return "Order successfully paid!";
    }

    private List<SessionCreateParams.LineItem> fullLineItems(Long orderId){

        List<SessionCreateParams.LineItem> lineItemList = new ArrayList<>();

        Order order = orderService.getOrderById(orderId);

        orderCheck(order);

        List<OrderDetails> orderDetailsList = order.getOrderDetails();

        for (OrderDetails orderDetails: orderDetailsList){
            String productName = orderDetails.getVariationDetails().getProductVariation().getProduct().getName();
            Long quantity = orderDetails.getQuantity().longValue();
            String imageLink = orderDetails.getVariationDetails().getProductVariation().getVariationImage().getImageLink();
            BigDecimal price = orderDetails.getTotalDetailPrice()
                    .divide(BigDecimal.valueOf(orderDetails.getQuantity()), RoundingMode.UP)
                    .multiply(BigDecimal.valueOf(10));

            SessionCreateParams.LineItem lineItem = SessionCreateParams.LineItem.builder()
                    .setQuantity(quantity)
                    .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                            .setUnitAmountDecimal(price)
                            .setCurrency(CURRENCY)
                            .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                    .setName(productName)
                                    .addImage(imageLink)
                                    .build())
                            .build())
                    .build();

            lineItemList.add(lineItem);
        }

        return lineItemList;
    }

    private void orderCheck(Order order){
        if (order.getPaymentMethod() != PaymentMethod.CARD && order.getOrderStatus() != OrderStatus.WAITING_FOR_PAYMENT){
            throw new OrderException("Order already paid or paying method is cash!");
        }
    }
}
