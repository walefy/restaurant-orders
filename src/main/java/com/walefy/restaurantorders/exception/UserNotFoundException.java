package com.walefy.restaurantorders.exception;

public class UserNotFoundException extends NotFoundException {

  public UserNotFoundException() {
    super("User not found!");
  }
}
