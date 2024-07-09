package com.walefy.restaurantorders.dto;

import com.walefy.restaurantorders.entity.Product;

public record ProductReturnDto(Long id, String name, Float price, String description, String imageUrl) {
  public static ProductReturnDto fromEntity(Product product) {
    return new ProductReturnDto(
      product.getId(),
      product.getName(),
      product.getPrice(),
      product.getDescription(),
      product.getImageUrl()
    );
  }
}
