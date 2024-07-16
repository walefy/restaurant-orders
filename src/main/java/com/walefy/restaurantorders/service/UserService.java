package com.walefy.restaurantorders.service;

import com.walefy.restaurantorders.dto.UserCreateDto;
import com.walefy.restaurantorders.entity.Product;
import com.walefy.restaurantorders.entity.User;
import com.walefy.restaurantorders.exception.ProductNotFoundException;
import com.walefy.restaurantorders.exception.UserNotFoundException;
import com.walefy.restaurantorders.repository.UserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
  private final UserRepository userRepository;
  private final ProductService productService;

  @Autowired
  public UserService(UserRepository userRepository, ProductService productService) {
    this.userRepository = userRepository;
    this.productService = productService;
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

  @Transactional
  public User addProductsInCart(Long userId, List<Long> productsIds)
    throws ProductNotFoundException, UserNotFoundException {
    User user = this.findById(userId);
    List<Product> products = user.getCart();

    for (long id : productsIds) {
      Product product = this.productService.findById(id);
      products.add(product);
    }

    return this.userRepository.save(user);
  }

  public void delete(Long id) throws UserNotFoundException {
    User user = this.findById(id);
    this.userRepository.delete(user);
  }
}
