package com.wanderlust.wanderlust_app.businessOwner.web;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/business-owner")
public class BusinessOwnerController {

    @GetMapping
    public ResponseEntity<String> index() {
        return ResponseEntity.ok("Welcome to Wanderlust");
    }
}
