package com.example.bisneslogic.repositories;

import com.example.bisneslogic.models.Cart;
import com.example.bisneslogic.models.CartItem;
import com.example.bisneslogic.models.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
   List<CartItem> findAll();

   Optional<CartItem> findByProductAndCart(Product product, Cart cart);

   List<CartItem> findByCart(Cart cart);
}
