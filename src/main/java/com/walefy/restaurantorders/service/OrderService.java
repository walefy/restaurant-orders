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
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {
  private final OrderRepository orderRepository;
  private final OrderProductRepository orderProductRepository;
  private final ProductService productService;
  private final UserService userService;

  @Autowired
  public OrderService(
    OrderRepository orderRepository,
    ProductService productService,
    UserService userService,
    OrderProductRepository orderProductRepository
  ) {
    this.orderRepository = orderRepository;
    this.productService = productService;
    this.userService = userService;
    this.orderProductRepository = orderProductRepository;
  }

  @Transactional
  public Order create(OrderCreateDto orderCreate)
    throws ProductNotFoundException, UserNotFoundException {
    User user = this.userService.findById(orderCreate.userId());
    Order order = new Order(user);
    List<OrderProduct> orderProducts = new ArrayList<>();

    for (long id : orderCreate.productsIds()) {
      Product product = this.productService.findById(id);
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
