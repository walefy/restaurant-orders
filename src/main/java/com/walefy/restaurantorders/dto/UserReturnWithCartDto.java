package com.walefy.restaurantorders.dto;

import com.walefy.restaurantorders.entity.User;
import com.walefy.restaurantorders.security.user.Role;
import java.util.List;

public record UserReturnWithCartDto(
  Long id,
  String name,
  String email,
  Role role,
  String imageUrl,
  List<ProductReturnDto> cart
) {
  public static UserReturnWithCartDto fromEntity(User user) {
    return new UserReturnWithCartDto(
      user.getId(),
      user.getName(),
      user.getEmail(),
      user.getRole(),
      user.getImageUrl(),
      user.getCart().stream().map(ProductReturnDto::fromEntity).toList()
    );
  }
}
