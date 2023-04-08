package com.example.bisneslogic.services;

import com.example.bisneslogic.common.CartNotFoundException;
import com.example.bisneslogic.common.OrderNotFoundException;
import com.example.bisneslogic.dto.ScoreDto;
import com.example.bisneslogic.dto.cart.item.CartItemDto;
import com.example.bisneslogic.dto.order.OrderDto;
import com.example.bisneslogic.models.Delivery;
import com.example.bisneslogic.models.Order;
import com.example.bisneslogic.repositories.CartItemRepository;
import com.example.bisneslogic.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService  {

    private OrderRepository orderRepository;

    private CartServices cartServices;

    private CartItemService cartItemService;
    private  CartItemRepository cartItemRepository;

    private DeliveryService deliveryService;

    @Autowired
    public OrderService(OrderRepository orderRepository, CartServices cartServices,
                        CartItemService cartItemService, CartItemRepository cartItemRepository, DeliveryService deliveryService) {
        this.orderRepository = orderRepository;
        this.cartServices = cartServices;
        this.cartItemService = cartItemService;
        this.cartItemRepository = cartItemRepository;
        this.deliveryService = deliveryService;
    }

    public Order createOrder(ScoreDto scoreDto) {
        Order order = new Order();
        Delivery delivery = new Delivery(scoreDto.getAddress(),scoreDto.getDeliveryDate());
        order.setCart(cartServices.getCurrentCart());
        cartServices.createCart();
        deliveryService.createDelivery(delivery);
        order.setDelivery(delivery);
        orderRepository.save(order);
        return order;
    }



    public List<Order> findAll(){
        return orderRepository.findAll();
    }

    public List<OrderDto> listOrderItems(){
        final List<Order> orderList = findAll();
        List<OrderDto> listOrders = new ArrayList<>();
        for (Order order : orderList){
            CartItemDto cartItemDto = cartItemService.listCartItemsByCart(order.getCart());

            OrderDto orderDto = new OrderDto();
            orderDto.setCartItem(cartItemDto.getCartItem());
            orderDto.setAmount(cartItemDto.getTotalCost());
            orderDto.setAddress(order.getDelivery().getAddress());
            orderDto.setDeliveryDate(order.getDelivery().getDeliveryDate());
            listOrders.add(orderDto);

        }
        return listOrders;
    }

    public OrderDto OrdersItems(Long id) throws CartNotFoundException, OrderNotFoundException {
        CartItemDto cartItemDto = cartItemService.listCartItemsByCart(cartServices.findById(id));
        OrderDto orderDto = new OrderDto();
        orderDto.setCartItem(cartItemDto.getCartItem());
        orderDto.setAmount(cartItemDto.getTotalCost());
        orderDto.setAddress(orderRepository.findById(id).orElseThrow(()-> new OrderNotFoundException("Order does nor exist")).getDelivery().getAddress());
        orderDto.setDeliveryDate(orderRepository.findById(id).orElseThrow(()-> new OrderNotFoundException("Order does nor exist")).getDelivery().getDeliveryDate());
        return orderDto;

    }


//    public CartItemDto listCartItems() {
//
//        final List<CartItem> cartItemList = cartItemRepository.findByCart(cartServices.getCurrentCart());
//
//        List<CartItemListDto> cartItems = new ArrayList<>();
//
//        Double totalCost = 0.0;
//
//        for (CartItem cartItem : cartItemList){
//            CartItemListDto cartItemListDto = new CartItemListDto(cartItem);
//            totalCost += cartItemListDto.getQuantity() * cartItemListDto.getProduct().getPrice();
//            cartItems.add(cartItemListDto);
//        }
//
//        CartItemDto cartItemDto =new CartItemDto();
//        cartItemDto.setTotalCost(totalCost);
//        cartItemDto.setCartItem(cartItems);
//
//        return cartItemDto;
//
//    }
}
