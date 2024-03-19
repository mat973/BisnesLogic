package com.example.bisneslogic.controllers;

import com.example.bisneslogic.common.ApiResponse;
import com.example.bisneslogic.models.Cart;
import com.example.bisneslogic.repositories.CartRepository;
import com.example.bisneslogic.repositories.UserRepository;
import com.example.bisneslogic.services.CartServices;
import com.example.bisneslogic.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/cart")
public class CartController {



    private final CartServices cartServices;


 @Autowired
    private UserRepository userRepository;


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
