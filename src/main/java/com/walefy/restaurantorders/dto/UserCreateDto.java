package com.walefy.restaurantorders.dto;

import com.walefy.restaurantorders.entity.User;
import com.walefy.restaurantorders.security.user.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserCreateDto(
  @NotBlank(message = "name attribute must not be blank")
  @Size(min = 3, message = "name must have more than 3 characters")
  String name,
  @NotBlank(message = "email attribute must not be blank")
  @Email(message = "email attribute must be a valid email")
  String email,
  @NotBlank(message = "password attribute must not be blank")
  @Size(min = 6, message = "password must have more than 6 characters")
  String password,
  @NotNull(message = "role attribute must not be blank! Try USER or ADMIN")
  Role role,
  String imageUrl,
  String adminToken
) {
  public User toEntity() {
    return new User(name, email, password, role, imageUrl);
  }
}
