package com.example.bisneslogic.dto.cart.item;

import com.example.bisneslogic.models.Cart;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.NotNull;

public class AddToCartItemDto {
    private Long id;
    @JsonView
    private @NotNull Long productId;
    @JsonView
    private @NotNull Integer quantity;



    public AddToCartItemDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
