package com.walefy.restaurantorders.controller;

import com.walefy.restaurantorders.dto.OrderCreateDto;
import com.walefy.restaurantorders.dto.OrderReturnDto;
import com.walefy.restaurantorders.entity.Order;
import com.walefy.restaurantorders.exception.ProductNotFoundException;
import com.walefy.restaurantorders.exception.UserNotFoundException;
import com.walefy.restaurantorders.service.OrderService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

  @PostMapping
  public ResponseEntity<OrderReturnDto> create(@RequestBody @Valid OrderCreateDto orderCreate)
    throws ProductNotFoundException, UserNotFoundException {
    Order order = this.orderService.create(orderCreate);
    return ResponseEntity.status(HttpStatus.CREATED).body(OrderReturnDto.fromEntity(order));
  }

  @GetMapping
  @PreAuthorize("hasAuthority('ADMIN')")
  public ResponseEntity<List<OrderReturnDto>> findAll() {
    List<OrderReturnDto> orders = this.orderService
      .findAll()
      .stream()
      .map(OrderReturnDto::fromEntity)
      .toList();

    return ResponseEntity.status(HttpStatus.OK).body(orders);
  }
}
