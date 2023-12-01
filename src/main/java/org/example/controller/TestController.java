package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RequiredArgsConstructor
@RequestMapping("/test")
@RestController
public class TestController {
    @GetMapping
    public String testMethod(){
        return "Works!";
    }
}
