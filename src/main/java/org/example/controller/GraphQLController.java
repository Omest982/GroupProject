package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.entity.User;
import org.example.repository.UserRepository;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class GraphQLController{
    private final UserRepository userRepository;

    @QueryMapping
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

}
