package com.walefy.restaurantorders.dto;

import com.walefy.restaurantorders.entity.Order;
import com.walefy.restaurantorders.entity.Product;
import com.walefy.restaurantorders.exception.ProductNotFoundException;
import com.walefy.restaurantorders.repository.ProductRepository;
import java.util.ArrayList;
import java.util.List;

public record OrderCreateDto(Integer amount, List<Long> productsIds) {
  public Order toEntity(ProductRepository productRepository) throws ProductNotFoundException {
    Order order = new Order();
    order.setAmount(amount);

    List<Product> products = new ArrayList<>();

    for (Long id : productsIds) {
      Product product = productRepository
        .findById(id)
        .orElseThrow(() -> new ProductNotFoundException(id));

      products.add(product);
    }

    order.setProducts(products);
    return order;
  }
}
