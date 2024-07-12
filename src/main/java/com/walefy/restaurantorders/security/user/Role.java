package com.walefy.restaurantorders.security.user;

public enum Role {
  ADMIN("ADMIN"), USER("USER");

  public final String role;

  Role(String role) {
    this.role = role;
  }
}
