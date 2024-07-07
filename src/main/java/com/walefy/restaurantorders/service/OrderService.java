package com.walefy.restaurantorders.service;

import com.walefy.restaurantorders.dto.OrderCreateDto;
import com.walefy.restaurantorders.entity.Order;
import com.walefy.restaurantorders.exception.OrderNotFoundException;
import com.walefy.restaurantorders.exception.ProductNotFoundException;
import com.walefy.restaurantorders.repository.OrderRepository;
import com.walefy.restaurantorders.repository.ProductRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class OrderService {
  private final OrderRepository orderRepository;
  private final ProductRepository productRepository;

  @Autowired
  public OrderService(OrderRepository orderRepository, ProductRepository productRepository) {
    this.orderRepository = orderRepository;
    this.productRepository = productRepository;
  }

  public Order create(OrderCreateDto orderCreate) throws ProductNotFoundException {
    return this.orderRepository.save(orderCreate.toEntity(productRepository));
  }

  public List<Order> findAll() {
    return this.orderRepository.findAll();
  }

  public Order findById(Long id) throws OrderNotFoundException {
    return this.orderRepository.findById(id).orElseThrow(OrderNotFoundException::new);
  }

  public void delete(Long id) throws OrderNotFoundException {
    Order order = this.findById(id);
    this.orderRepository.delete(order);
  }
}
