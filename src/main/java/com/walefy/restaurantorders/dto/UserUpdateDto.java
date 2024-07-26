package com.walefy.restaurantorders.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserUpdateDto(
  @Size(min = 3, message = "name must have more than 3 characters")
  String name,
  @URL(message = "imageUrl attribute must have a URL")
  String imageUrl
) {}
