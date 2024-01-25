package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.DTO.NewShippingInfo;
import org.example.entity.ShippingInfo;
import org.example.entity.User;
import org.example.repository.UserRepository;
import org.example.service.ShippingInfoService;
import org.example.service.UserService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final ShippingInfoService shippingInfoService;

    @MutationMapping
    public ShippingInfo addShippingInfoToUser(@Argument Long userId,
                                       @Argument NewShippingInfo newShippingInfo){
        return shippingInfoService.addShippingInfoToUser(userId, newShippingInfo);
    }

}
