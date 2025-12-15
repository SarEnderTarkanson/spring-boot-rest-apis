package com.luv2code.books.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {

    @GetMapping("/api")
    public String firstAPI() {
        return "Hello Eric!";
    }
}
