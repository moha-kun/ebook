package com.moha.ebook.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/test")
@RestController
public class HelloWorldController {

    @GetMapping("/hello")
    public ResponseEntity<Ball> sayHello() {
        return new ResponseEntity<>(new Ball("blue", "Mohamed"), HttpStatus.OK);
    }

    public record Ball(String color, String name) {
    }

}

