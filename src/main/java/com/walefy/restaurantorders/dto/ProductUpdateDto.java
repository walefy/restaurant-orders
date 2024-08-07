package com.walefy.restaurantorders.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public record ProductUpdateDto(
  @Size(min = 3, message = "name must have more than 3 characters")
  String name,
  @Size(min = 3, message = "description must have more than 10 characters")
  String description,
  @Min(value = 0, message = "price attribute must be greater than zero")
  Float price,
  String imageUrl
) {}
