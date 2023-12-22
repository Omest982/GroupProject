package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/test")
@RestController
public class TestController {
    @GetMapping
    public String testMethod(){
        String test = "12345-6789";
        return test.substring(5);
    }
}
