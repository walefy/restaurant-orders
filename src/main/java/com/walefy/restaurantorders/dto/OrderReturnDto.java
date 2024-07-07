package com.walefy.restaurantorders.dto;

import com.walefy.restaurantorders.entity.Order;
import com.walefy.restaurantorders.entity.Product;
import java.util.List;

public record OrderReturnDto(Long id, Integer amount, List<Product> products) {
  public static OrderReturnDto fromEntity(Order order) {
    return new OrderReturnDto(order.getId(), order.getAmount(), order.getProducts());
  }
}
