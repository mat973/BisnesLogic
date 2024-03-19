package com.example.bisneslogic.services;

import com.example.bisneslogic.common.ApiResponse;
import com.example.bisneslogic.common.CartNotFoundException;
import com.example.bisneslogic.common.ProductNotFoundException;
import com.example.bisneslogic.models.Cart;
import com.example.bisneslogic.models.UserInfo;
import com.example.bisneslogic.repositories.CartRepository;
import com.example.bisneslogic.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Optional;
import java.util.logging.Logger;


@Service
public class CartServices {

    private final CartRepository cartRepository;



    @Autowired
    UserRepository userRepository;
    @Autowired
    public CartServices(CartRepository cartRepository) {
        this.cartRepository = cartRepository;

    }

    public ResponseEntity<ApiResponse> createCart() {
        Cart cart = new Cart();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Получаем имя текущего пользователя
        String username = authentication.getName();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        // Получаем пользователя по имени
        UserInfo user = userRepository.findByUsername(username);

        if (user != null) {
            // Устанавливаем пользователя в корзину
            cart.setUserInfo(user);
            cart.setCreationDate(timestamp);

            // Сохраняем корзину
            cartRepository.save(cart);

            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            // Пользователь не найден
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public Cart getCurrentCart() {
        Cart cart = cartRepository.findMyCurrentCart();

        return cart;
    }

    public Cart findById(Long id) throws CartNotFoundException {
       return cartRepository.findById(id).orElseThrow(()->new CartNotFoundException("This cart does not exist "));
    }
}
