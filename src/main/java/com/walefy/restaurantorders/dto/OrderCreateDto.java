package com.walefy.restaurantorders.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

public record OrderCreateDto(
  @NotNull(message = "userId attribute must not be null")
  Long userId,
  @NotNull(message = "productsIds attribute must not be null")
  @Size(min = 1, message = "productsIds attribute must have at least one id")
  List<Long> productsIds
) {}
