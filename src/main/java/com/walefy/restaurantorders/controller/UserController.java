package com.walefy.restaurantorders.controller;

import com.walefy.restaurantorders.dto.UserCreateDto;
import com.walefy.restaurantorders.dto.UserReturnDto;
import com.walefy.restaurantorders.entity.User;
import com.walefy.restaurantorders.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
  public ResponseEntity<UserReturnDto> create(@RequestBody UserCreateDto userCreate) {
    User user = this.userService.create(userCreate);
    return ResponseEntity.status(HttpStatus.CREATED).body(UserReturnDto.fromEntity(user));
  }

  @GetMapping
  public ResponseEntity<List<UserReturnDto>> findAll() {
    List<UserReturnDto> users = this.userService
      .findAll()
      .stream()
      .map(UserReturnDto::fromEntity)
      .toList();

    return ResponseEntity.status(HttpStatus.OK).body(users);
  }
}
