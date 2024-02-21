package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.entity.enums.*;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@Slf4j
public class EnumController {
    @QueryMapping
    @Cacheable("classifications")
    public List<Classification> getAllClassifications(){
        log.info("Method activated!");
        return List.of(Classification.values());
    }

    @QueryMapping
    @Cacheable("sexes")
    public List<Sex> getAllSexes(){
        return List.of(Sex.values());
    }

    @QueryMapping
    @Cacheable("paymentMethods")
    public List<PaymentMethod> getAllPaymentMethods(){
        return List.of(PaymentMethod.values());
    }
    @QueryMapping
    @Cacheable("shippingFroms")
    public List<ShippingFrom> getAllShippingFrom(){
        return List.of(ShippingFrom.values());
    }

    @QueryMapping
    @Cacheable("productStatuses")
    public List<ProductStatus> getAllProductStatuses(){
        return List.of(ProductStatus.values());
    }
}
