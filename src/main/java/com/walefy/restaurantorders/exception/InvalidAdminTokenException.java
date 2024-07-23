package com.walefy.restaurantorders.exception;

public class InvalidAdminTokenException extends Exception {
  public InvalidAdminTokenException() {
    super("Invalid admin token!");
  }
}
