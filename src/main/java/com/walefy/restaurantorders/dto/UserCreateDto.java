package com.walefy.restaurantorders.dto;

import com.walefy.restaurantorders.entity.User;
import com.walefy.restaurantorders.security.user.Role;

public record UserCreateDto(String name, String email, String password, Role role, String imageUrl) {
  public User toEntity() {
    return new User(name, email, password, role, imageUrl);
  }
}
