package com.example.bisneslogic.dto.order;

import com.example.bisneslogic.dto.cart.item.CartItemDto;
import com.example.bisneslogic.dto.cart.item.CartItemListDto;
import com.example.bisneslogic.models.Delivery;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.Column;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public class OrderDto {

    private Long id;
    private List<CartItemListDto> cartItem;

//    private String deliver;


    @JsonView
    private String address;


    @JsonView
    private LocalDate deliveryDate;
    private Double  amount;



    public OrderDto() {
    }




    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }


    public List<CartItemListDto> getCartItem() {
        return cartItem;
    }

    public void setCartItem(List<CartItemListDto> cartItem) {
        this.cartItem = cartItem;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }
}
