package com.example.bisneslogic.services;

import com.example.bisneslogic.common.CustomExeption;
import com.example.bisneslogic.common.ProductNotFoundException;
import com.example.bisneslogic.dto.cart.item.AddToCartItemDto;
import com.example.bisneslogic.dto.cart.item.CartItemDto;
import com.example.bisneslogic.dto.cart.item.CartItemListDto;
import com.example.bisneslogic.dto.cart.item.DeleteFromCartItemDto;
import com.example.bisneslogic.models.Cart;
import com.example.bisneslogic.models.CartItem;
import com.example.bisneslogic.models.Product;

import com.example.bisneslogic.repositories.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartItemService {

    @Autowired
    ProductService productService;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private CartServices cartServices;

    public CartItemService() {
    }
    @Autowired
    public CartItemService(ProductService productService, CartItemRepository cartItemRepository) {
        this.productService = productService;

        this.cartItemRepository = cartItemRepository;
    }

    public void addToCart(AddToCartItemDto addToCartItemDto) throws ProductNotFoundException {
        Product product = productService.findOne(addToCartItemDto.getProductId());

        CartItem cartItem = new CartItem();


        cartItem.setProduct(product);

        cartItem.setQuantity(addToCartItemDto.getQuantity());

        cartItem.setCart(cartServices.getCurrentCart());

        cartItemRepository.save(cartItem);
    }

    public CartItemDto listCartItems() {

        final List<CartItem> cartItemList = cartItemRepository.findByCart(cartServices.getCurrentCart());

        List<CartItemListDto> cartItems = new ArrayList<>();

        Double totalCost = 0.0;

        for (CartItem cartItem : cartItemList){
            CartItemListDto cartItemListDto = new CartItemListDto(cartItem);
            totalCost += cartItemListDto.getQuantity() * cartItemListDto.getProduct().getPrice();
            cartItems.add(cartItemListDto);
        }

        CartItemDto cartItemDto =new CartItemDto();
        cartItemDto.setTotalCost(totalCost);
        cartItemDto.setCartItem(cartItems);

        return cartItemDto;

    }


    public CartItemDto listCartItemsByCart(Cart cart) {

        final List<CartItem> cartItemList = cartItemRepository.findByCart(cart);

        List<CartItemListDto> cartItems = new ArrayList<>();

        Double totalCost = 0.0;

        for (CartItem cartItem : cartItemList){
            CartItemListDto cartItemListDto = new CartItemListDto(cartItem);
            totalCost += cartItemListDto.getQuantity() * cartItemListDto.getProduct().getPrice();
            cartItems.add(cartItemListDto);
        }

        CartItemDto cartItemDto =new CartItemDto();
        cartItemDto.setTotalCost(totalCost);
        cartItemDto.setCartItem(cartItems);

        return cartItemDto;

    }


    public void deleteCartItem(Long cartItemsId) {

        Optional<CartItem> optionalCart = cartItemRepository.findById(cartItemsId);

        if (optionalCart.isEmpty()){
            throw new CustomExeption("Cart item id is invalid: " + cartItemsId);
        }

        CartItem cartItem = optionalCart.get();

        cartItemRepository.delete(cartItem);
    }

    public void deleteCartByName(DeleteFromCartItemDto deleteFromCartItemDto) throws ProductNotFoundException {
       Product product = productService.findByTitle(deleteFromCartItemDto.getTitle()).orElseThrow(()->new ProductNotFoundException("There is no product with this name "));

        Optional<CartItem> optionalCart = cartItemRepository.findByProductAndCart(product, cartServices.getCurrentCart());

        CartItem cartItem = optionalCart.orElseThrow(()->new ProductNotFoundException("There is no such item in your cart "));

        cartItemRepository.delete(cartItem);

    }


}
