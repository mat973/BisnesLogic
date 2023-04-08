package com.example.bisneslogic.services;

import com.example.bisneslogic.common.CartNotFoundException;
import com.example.bisneslogic.common.ProductNotFoundException;
import com.example.bisneslogic.models.Cart;
import com.example.bisneslogic.repositories.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Optional;


@Service
public class CartServices {

    private final CartRepository cartRepository;


    @Autowired
    public CartServices(CartRepository cartRepository) {
        this.cartRepository = cartRepository;

    }

    public void createCart() {
        Cart cart = new Cart();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        cart.setCreationDate(timestamp);

        cartRepository.save(cart);
    }

    public Cart getCurrentCart() {
        Cart cart = cartRepository.findMyCurrentCart();

        return cart;
    }

    public Cart findById(Long id) throws CartNotFoundException {
       return cartRepository.findById(id).orElseThrow(()->new CartNotFoundException("This cart does not exist "));
    }
}
