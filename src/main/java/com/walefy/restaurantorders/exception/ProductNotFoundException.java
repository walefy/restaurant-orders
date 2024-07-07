package com.walefy.restaurantorders.exception;

public class ProductNotFoundException extends NotFoundException{
  public ProductNotFoundException(Long productId) {
    super("Product with id %o not found!".formatted(productId));
  }
}
