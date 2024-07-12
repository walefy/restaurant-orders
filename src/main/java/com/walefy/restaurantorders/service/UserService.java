package com.walefy.restaurantorders.service;

import com.walefy.restaurantorders.dto.UserCreateDto;
import com.walefy.restaurantorders.entity.User;
import com.walefy.restaurantorders.exception.UserNotFoundException;
import com.walefy.restaurantorders.repository.UserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  private final UserRepository userRepository;

  @Autowired
  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User create(UserCreateDto userCreate) {
    return this.userRepository.save(userCreate.toEntity());
  }

  public List<User> findAll() {
    return this.userRepository.findAll();
  }

  public User findByEmail(String email) throws UserNotFoundException {
    return this.userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
  }

  public User findById(Long id) throws UserNotFoundException {
    return this.userRepository.findById(id).orElseThrow(UserNotFoundException::new);
  }

  public void delete(Long id) throws UserNotFoundException {
    User user = this.findById(id);
    this.userRepository.delete(user);
  }
}
