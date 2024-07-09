package com.walefy.restaurantorders.dto;

import com.walefy.restaurantorders.entity.Order;
import com.walefy.restaurantorders.entity.OrderProduct;
import com.walefy.restaurantorders.entity.Product;
import com.walefy.restaurantorders.exception.ProductNotFoundException;
import com.walefy.restaurantorders.repository.ProductRepository;
import java.util.ArrayList;
import java.util.List;

public record OrderCreateDto(Integer amount, List<Long> productsIds) {
  public Order toEntity(ProductRepository productRepository) throws ProductNotFoundException {
    Order order = new Order();

    List<OrderProduct> orderProducts = new ArrayList<>();

    for (Long id : productsIds) {
      Product product = productRepository
        .findById(id)
        .orElseThrow(() -> new ProductNotFoundException(id));

      orderProducts.add(new OrderProduct(order, product));
    }

    order.setOrderProducts(orderProducts);

    return order;
  }
}
