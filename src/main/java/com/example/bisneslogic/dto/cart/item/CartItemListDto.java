package com.example.bisneslogic.dto.cart.item;

import com.example.bisneslogic.models.CartItem;
import com.example.bisneslogic.models.Product;

public class CartItemListDto {
    private Long id;
    private Integer quantity;

    private Product product;

    public CartItemListDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public CartItemListDto(CartItem cartItem) {
        this.id = cartItem.getId();
        this.quantity = cartItem.getQuantity();
        this.setProduct(cartItem.getProduct());
    }
}
