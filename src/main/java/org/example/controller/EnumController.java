package org.example.controller;

import org.example.entity.enums.Classification;
import org.example.entity.enums.PaymentMethod;
import org.example.entity.enums.Sex;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class EnumController {
    @QueryMapping
    public List<Classification> getAllClassifications(){
        return List.of(Classification.values());
    }

    @QueryMapping
    public List<Sex> getAllSexes(){
        return List.of(Sex.values());
    }

    @QueryMapping
    public List<PaymentMethod> getAllPaymentMethods(){
        return List.of(PaymentMethod.values());
    }
}
