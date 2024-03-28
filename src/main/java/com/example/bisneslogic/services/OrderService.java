package com.example.bisneslogic.services;

import com.example.bisneslogic.common.CartNotFoundException;
import com.example.bisneslogic.common.OrderNotFoundException;
import com.example.bisneslogic.dto.ScoreDto;
import com.example.bisneslogic.dto.cart.item.CartItemDto;
import com.example.bisneslogic.dto.order.OrderDto;
import com.example.bisneslogic.models.Delivery;
import com.example.bisneslogic.models.Order;
import com.example.bisneslogic.models.UserInfo;
import com.example.bisneslogic.repositories.CartItemRepository;
import com.example.bisneslogic.repositories.OrderRepository;
import com.example.bisneslogic.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService  {

    private OrderRepository orderRepository;
    private UserRepository userRepository;
    private CartServices cartServices;

    private CartItemService cartItemService;
    private  CartItemRepository cartItemRepository;

    private DeliveryService deliveryService;


    private EmailService emailService;

//    private KafkaTemplate<String , String> kafkaTemplate;

//    @Value("${kafka.emailTopic}")
//    private String emailTopic;

    @Autowired
    public OrderService(OrderRepository orderRepository, UserRepository userRepository, CartServices cartServices,
                        CartItemService cartItemService, CartItemRepository cartItemRepository, DeliveryService deliveryService, EmailService emailService) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.cartServices = cartServices;
        this.cartItemService = cartItemService;
        this.cartItemRepository = cartItemRepository;
        this.deliveryService = deliveryService;


        this.emailService = emailService;
    }

    @Transactional()
    public Order createOrder(ScoreDto scoreDto)   {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();
        UserInfo user = userRepository.findByUsername(username);


        CartItemDto cartItemDto= cartItemService.listCartItems();
        if(user.getMoney() > cartItemDto.getTotalCost()) {
            user.setMoney(user.getMoney() - cartItemDto.getTotalCost());
            Order order = new Order();
            Delivery delivery = new Delivery(scoreDto.getAddress(), scoreDto.getDeliveryDate());
            order.setCart(cartServices.getCurrentCart());
            cartServices.createCart();
            deliveryService.createDelivery(delivery);
            order.setDelivery(delivery);
            orderRepository.save(order);
            emailService.sendEmail();
            //kafkaTemplate.send(emailTopic, "Новыйзаказ создан"+ order.getId() );
            return order;
        }else throw new RuntimeException("Недостаточно денег на счете пользователя!");
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
