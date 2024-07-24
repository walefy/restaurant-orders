package com.walefy.restaurantorders.dto;

import com.walefy.restaurantorders.entity.Product;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ProductCreateDto(
  @NotBlank(message = "name attribute must not be blank")
  @Size(min = 3, message = "name must have more than 3 characters")
  String name,
  @NotBlank(message = "description attribute must not be blank")
  @Size(min = 3, message = "description must have more than 10 characters")
  String description,
  @NotNull(message = "price attribute must not be null")
  @Min(value = 0, message = "price attribute must be greater than zero")
  Float price,
  String imageUrl
) {
  public Product toEntity() {
    return new Product(name, description, price, imageUrl);
  }
}
