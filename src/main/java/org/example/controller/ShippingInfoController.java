package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.entity.ShippingInfo;
import org.example.service.ShippingInfoService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ShippingInfoController {
    private final ShippingInfoService shippingInfoService;

    @QueryMapping
    public List<ShippingInfo> getAllShippingInfosByUserId(@Argument Long userId){
        return shippingInfoService.getAllShippingInfosByUserId(userId);
    }

    @QueryMapping
    public ShippingInfo getShippingInfoById(@Argument Long shippingInfoId){
        return shippingInfoService.getShippingInfoById(shippingInfoId);
    }

    @QueryMapping
    public List<ShippingInfo> getAllShippingInfos(){
        return shippingInfoService.getAllShippingInfo();
    }
}
