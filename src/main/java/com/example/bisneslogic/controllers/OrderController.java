package com.example.bisneslogic.controllers;

import com.example.bisneslogic.common.*;
import com.example.bisneslogic.dto.ScoreDto;
import com.example.bisneslogic.dto.cart.item.AddToCartItemDto;
import com.example.bisneslogic.dto.order.OrderDto;
import com.example.bisneslogic.models.Order;
import com.example.bisneslogic.models.Product;
import com.example.bisneslogic.services.OrderService;


import com.example.bisneslogic.util.CardNotValidException;
import com.example.bisneslogic.util.ProductNotCreatedException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

//    ResponseEntity<ApiResponse>

    @PostMapping("")
    public ResponseEntity<ApiResponse> createOrder(@RequestBody @Valid ScoreDto scoreDto, HttpServletRequest request) throws DateNotValidException, BadRequestException {
        if (request.getParameterMap().isEmpty()) {


            if (scoreDto.getDeliveryDate().isBefore(LocalDate.now().plusDays(3)) || scoreDto.getDeliveryDate().isAfter(LocalDate.now().plusDays(60))) {
                throw new DateNotValidException("Date must be between 3 and 60 days from today");
            }
            orderService.createOrder(scoreDto);
            return new ResponseEntity<>(new ApiResponse(true, "Order was created"), HttpStatus.CREATED);

        }else  throw new BadRequestException("Query Params not allowed for this endpoint");
    }


    @GetMapping("")
    public List<OrderDto> getAllOrder(){

        return (orderService.listOrderItems());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOneOrder(@PathVariable("id") Long id) throws CartNotFoundException, OrderNotFoundException {

        return new ResponseEntity<>(orderService.OrdersItems(id), HttpStatus.OK);

    }



}
