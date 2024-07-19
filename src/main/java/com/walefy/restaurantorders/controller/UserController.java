package com.walefy.restaurantorders.controller;

import com.walefy.restaurantorders.dto.ProductsIdsDto;
import com.walefy.restaurantorders.dto.UserCreateDto;
import com.walefy.restaurantorders.dto.UserReturnDto;
import com.walefy.restaurantorders.dto.UserReturnWithCartDto;
import com.walefy.restaurantorders.entity.User;
import com.walefy.restaurantorders.exception.ProductNotFoundException;
import com.walefy.restaurantorders.exception.UserAlreadyRegistered;
import com.walefy.restaurantorders.exception.UserNotFoundException;
import com.walefy.restaurantorders.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping
  public ResponseEntity<UserReturnDto> create(@RequestBody UserCreateDto userCreate)
    throws UserAlreadyRegistered {
    User user = this.userService.create(userCreate);
    return ResponseEntity.status(HttpStatus.CREATED).body(UserReturnDto.fromEntity(user));
  }

  @PostMapping("{userId}/product/cart/")
  public ResponseEntity<UserReturnWithCartDto> addProductsInCart(
    @PathVariable Long userId,
    @RequestBody ProductsIdsDto productsIdsDto
  ) throws UserNotFoundException, ProductNotFoundException {
    User user = this.userService.addProductsInCart(userId, productsIdsDto.productsIds());

    return ResponseEntity.status(HttpStatus.OK).body(UserReturnWithCartDto.fromEntity(user));
  }

  @DeleteMapping("{userId}/product/cart/")
  public ResponseEntity<UserReturnWithCartDto> removeProductsFromCart(
    @PathVariable Long userId,
    @RequestBody ProductsIdsDto productsIdsDto
  ) throws UserNotFoundException, ProductNotFoundException {
    User user = this.userService.removeProductsFromCart(userId, productsIdsDto.productsIds());

    return ResponseEntity.status(HttpStatus.OK).body(UserReturnWithCartDto.fromEntity(user));
  }

  @GetMapping
  @PreAuthorize("hasAuthority('ADMIN')")
  public ResponseEntity<List<UserReturnDto>> findAll() {
    List<UserReturnDto> users = this.userService
      .findAll()
      .stream()
      .map(UserReturnDto::fromEntity)
      .toList();

    return ResponseEntity.status(HttpStatus.OK).body(users);
  }

  @DeleteMapping("/{userId}")
  @PreAuthorize("hasAuthority('ADMIN')")
  public ResponseEntity<Void> deleteById(@PathVariable Long id) throws UserNotFoundException {
    this.userService.deleteById(id);

    return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
  }
}
