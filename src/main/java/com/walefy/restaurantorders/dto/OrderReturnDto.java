package com.walefy.restaurantorders.dto;

import com.walefy.restaurantorders.entity.Order;
import com.walefy.restaurantorders.entity.OrderProduct;
import com.walefy.restaurantorders.entity.User;
import java.util.List;

public record OrderReturnDto(Long id, User user, List<OrderProduct> products) {
  public static OrderReturnDto fromEntity(Order order) {
    return new OrderReturnDto(order.getId(), order.getUser(), order.getOrderProducts());
  }
}
