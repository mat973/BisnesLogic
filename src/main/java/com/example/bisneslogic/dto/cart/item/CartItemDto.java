package com.example.bisneslogic.dto.cart.item;

import java.util.List;

public class CartItemDto {
    private List<CartItemListDto> cartItem;
    private Double totalCost;

    public CartItemDto() {
    }

    public List<CartItemListDto> getCartItem() {
        return cartItem;
    }

    public void setCartItem(List<CartItemListDto> cartItem) {
        this.cartItem = cartItem;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }
}
