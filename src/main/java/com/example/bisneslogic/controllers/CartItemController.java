package com.example.bisneslogic.controllers;

import com.example.bisneslogic.common.ApiResponse;
import com.example.bisneslogic.common.ProductNotFoundException;
import com.example.bisneslogic.dto.cart.item.AddToCartItemDto;
import com.example.bisneslogic.dto.cart.item.CartItemDto;
import com.example.bisneslogic.dto.cart.item.DeleteFromCartItemDto;


import com.example.bisneslogic.services.CartItemService;
import com.example.bisneslogic.services.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController

@RequestMapping("/cart/item")
public class CartItemController {

    @Autowired
    private CartItemService cartItemService;


    @Autowired
    private ProductService productService;

    public CartItemController() {
    }
    @Autowired
    public CartItemController(CartItemService cartItemService, ProductService productService) {
        this.cartItemService = cartItemService;

        this.productService = productService;
    }



    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addToCart(@RequestBody AddToCartItemDto addToCartItemDto) throws ProductNotFoundException {
        cartItemService.addToCart(addToCartItemDto);
        return new ResponseEntity<>(new ApiResponse(true, "Added to cart"), HttpStatus.CREATED);
    }



    @GetMapping("")
    public ResponseEntity<CartItemDto> getCartItems(){
        CartItemDto cartItemDto = cartItemService.listCartItems();
        return new  ResponseEntity<>(cartItemDto, HttpStatus.OK);
    }

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<ApiResponse> deleteCartItem(@PathVariable("cartItemId") Long itemId){
        cartItemService.deleteCartItem(itemId);
        return new ResponseEntity<>(new ApiResponse(true, "Item has been removed"), HttpStatus.OK);
    }


    @DeleteMapping("")
    public ResponseEntity<ApiResponse> deleteCartItemName(@RequestBody DeleteFromCartItemDto deleteFromCartItemDto) throws ProductNotFoundException {


        cartItemService.deleteCartByName(deleteFromCartItemDto);

        return new ResponseEntity<>(new ApiResponse(true, "Item has been removed"), HttpStatus.OK);
    }
}
