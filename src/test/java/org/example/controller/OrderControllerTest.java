package org.example.controller;

import org.example.entity.Order;
import org.example.entity.enums.PaymentMethod;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runners.Suite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.GraphQlTester;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AutoConfigureGraphQlTester
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Suite.SuiteClasses({ProductControllerTest.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class OrderControllerTest {
    @Autowired
    private GraphQlTester graphQlTester;

    @Test
    public void shouldAddOrder(){
        Map<String, Object> newShippingInfo = new HashMap<>();
        newShippingInfo.put("region", "Kyiv region");
        newShippingInfo.put("city", "Kyiv");
        newShippingInfo.put("street", "Ushakova");
        newShippingInfo.put("house", "20");
        newShippingInfo.put("recipientFirstName", "Ivan");
        newShippingInfo.put("recipientLastName", "Poberezhets");
        newShippingInfo.put("recipientPhoneNumber", "123456");

        Map<String, Object> orderDetailsInfo = new HashMap<>();
        orderDetailsInfo.put("variationDetailsId", 1);
        orderDetailsInfo.put("quantity", 2);

        Map<String, Object> orderInfo = new HashMap<>();
        orderInfo.put("paymentMethod", PaymentMethod.CASH);

        graphQlTester.documentName("order_add")
                .variable("newShippingInfo", newShippingInfo)
                .variable("userId", 1L)
                .variable("orderDetailsInfo", List.of(orderDetailsInfo))
                .variable("orderInfo", orderInfo)
                .execute()
                .path("addOrder")
                .entity(Order.class)
                .satisfies(order -> {
                    Assertions.assertEquals(1L, order.getId());
                });
    }
}
