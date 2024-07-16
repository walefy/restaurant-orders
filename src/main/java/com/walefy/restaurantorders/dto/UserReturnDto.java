package com.walefy.restaurantorders.dto;

import com.walefy.restaurantorders.entity.User;
import com.walefy.restaurantorders.security.user.Role;

public record UserReturnDto(
  Long id,
  String name,
  String email,
  Role role,
  String imageUrl
) {
  public static UserReturnDto fromEntity(User user) {
    return new UserReturnDto(
      user.getId(),
      user.getName(),
      user.getEmail(),
      user.getRole(),
      user.getImageUrl()
    );
  }
}
