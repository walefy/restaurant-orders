package com.walefy.restaurantorders.exception;

public class UserAlreadyRegistered extends Exception {
  public UserAlreadyRegistered() {
    super("User already registered!");
  }
}
