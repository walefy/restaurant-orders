package com.walefy.restaurantorders.dto;

import com.walefy.restaurantorders.entity.Order;
import com.walefy.restaurantorders.entity.OrderProduct;
import com.walefy.restaurantorders.entity.Product;
import java.util.List;

public record OrderReturnDto(Long id, Long userId, List<ProductReturnDto> products) {
  public static OrderReturnDto fromEntity(Order order) {
    List<Product> products = order.getOrderProducts().stream().map(OrderProduct::getProduct).toList();
    List<ProductReturnDto> productsReturn = products.stream().map(ProductReturnDto::fromEntity).toList();
    return new OrderReturnDto(order.getId(), order.getUser().getId(), productsReturn);
  }
}
