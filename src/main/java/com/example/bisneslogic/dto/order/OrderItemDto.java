package com.example.bisneslogic.dto.order;

import com.example.bisneslogic.dto.cart.item.CartItemListDto;
import com.example.bisneslogic.models.Product;

public class OrderItemDto {

   private CartItemListDto cartItemListDto;

    public CartItemListDto getCartItemListDto() {
        return cartItemListDto;
    }

    public void setCartItemListDto(CartItemListDto cartItemListDto) {
        this.cartItemListDto = cartItemListDto;
    }
}
