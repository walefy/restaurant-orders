package com.walefy.restaurantorders.dto;

import com.walefy.restaurantorders.entity.Product;

public record ProductCreateDto(String name, String description, String imageUrl) {
  public Product toEntity() {
    return new Product(name, description, imageUrl);
  }
}
