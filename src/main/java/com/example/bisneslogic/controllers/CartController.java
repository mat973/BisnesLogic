package com.example.bisneslogic.controllers;

import com.example.bisneslogic.common.ApiResponse;
import com.example.bisneslogic.models.Cart;
import com.example.bisneslogic.repositories.CartRepository;
import com.example.bisneslogic.services.CartServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/cart")
public class CartController {



    private final CartServices cartServices;


    @Autowired
    public CartController(CartRepository cartRepository, CartServices cartServices) {

        this.cartServices = cartServices;
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse> createCart(){
        cartServices.createCart();
        return new ResponseEntity<>(new ApiResponse(true, "Cart was created"), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Cart> getCurrentCart(){
        Cart Cart = cartServices.getCurrentCart();

        return new ResponseEntity<>(Cart, HttpStatus.OK);
    }   
}
