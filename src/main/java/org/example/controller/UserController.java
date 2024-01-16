package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.DTO.NewAddress;
import org.example.entity.Address;
import org.example.entity.User;
import org.example.repository.UserRepository;
import org.example.service.AddressService;
import org.example.service.UserService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final AddressService addressService;
    //TODO: repository must be deleted to final version
    private final UserRepository userRepository;

    @QueryMapping
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @MutationMapping
    public Address addUserAddress(@Argument Long userId,
                                  @Argument NewAddress address){
        return addressService.addUserAddress(userId, address);
    }

}
