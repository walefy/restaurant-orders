package com.walefy.restaurantorders.controller;

import com.walefy.restaurantorders.dto.OrderCreateDto;
import com.walefy.restaurantorders.dto.OrderReturnDto;
import com.walefy.restaurantorders.entity.Order;
import com.walefy.restaurantorders.exception.ProductNotFoundException;
import com.walefy.restaurantorders.repository.OrderRepository;
import com.walefy.restaurantorders.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {
  private final OrderService orderService;

  @Autowired
  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  public ResponseEntity<OrderReturnDto> create(@RequestBody OrderCreateDto orderCreate)
    throws ProductNotFoundException {
    Order order = this.orderService.create(orderCreate);
    return ResponseEntity.status(HttpStatus.CREATED).body(OrderReturnDto.fromEntity(order));
  }
}
