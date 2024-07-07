package com.walefy.restaurantorders.exception;

public class OrderNotFoundException extends NotFoundException {
  public OrderNotFoundException() {
    super("Order not found!");
  }
}
