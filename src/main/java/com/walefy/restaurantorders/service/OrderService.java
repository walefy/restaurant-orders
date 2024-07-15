package com.walefy.restaurantorders.service;

import com.walefy.restaurantorders.dto.OrderCreateDto;
import com.walefy.restaurantorders.entity.Order;
import com.walefy.restaurantorders.entity.OrderProduct;
import com.walefy.restaurantorders.entity.Product;
import com.walefy.restaurantorders.entity.User;
import com.walefy.restaurantorders.exception.OrderNotFoundException;
import com.walefy.restaurantorders.exception.ProductNotFoundException;
import com.walefy.restaurantorders.exception.UserNotFoundException;
import com.walefy.restaurantorders.repository.OrderProductRepository;
import com.walefy.restaurantorders.repository.OrderRepository;
import com.walefy.restaurantorders.repository.ProductRepository;
import com.walefy.restaurantorders.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
  private final OrderRepository orderRepository;
  private final ProductRepository productRepository;
  private final UserRepository userRepository;
  private final OrderProductRepository orderProductRepository;

  @Autowired
  public OrderService(OrderRepository orderRepository, ProductRepository productRepository,
    UserRepository userRepository, OrderProductRepository orderProductRepository) {
    this.orderRepository = orderRepository;
    this.productRepository = productRepository;
    this.userRepository = userRepository;
    this.orderProductRepository = orderProductRepository;
  }

  public Order create(OrderCreateDto orderCreate)
    throws ProductNotFoundException, UserNotFoundException {
    User user = this.userRepository
      .findById(orderCreate.userId())
      .orElseThrow(UserNotFoundException::new);

    Order order = new Order(user);
    List<OrderProduct> orderProducts = new ArrayList<>();

    for (long id : orderCreate.productsIds()) {
      Product product = productRepository
        .findById(id)
        .orElseThrow(() -> new ProductNotFoundException(id));

      OrderProduct orderProduct = new OrderProduct(order, product);
      orderProducts.add(orderProduct);
    }

    order.setOrderProducts(orderProducts);

    Order orderUpdate = this.orderRepository.save(order);
    this.orderProductRepository.saveAll(orderProducts);
    return orderUpdate;
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
