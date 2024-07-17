package com.walefy.restaurantorders.service;

import com.walefy.restaurantorders.dto.UserCreateDto;
import com.walefy.restaurantorders.entity.Product;
import com.walefy.restaurantorders.entity.User;
import com.walefy.restaurantorders.exception.ProductNotFoundException;
import com.walefy.restaurantorders.exception.UserAlreadyRegistered;
import com.walefy.restaurantorders.exception.UserNotFoundException;
import com.walefy.restaurantorders.repository.UserRepository;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService implements UserDetailsService {
  private final UserRepository userRepository;
  private final ProductService productService;

  @Autowired
  public UserService(UserRepository userRepository, ProductService productService) {
    this.userRepository = userRepository;
    this.productService = productService;
  }

  public User create(UserCreateDto userCreate) throws UserAlreadyRegistered {
    Optional<User> userExists = this.userRepository.findByEmail(userCreate.email());

    if (userExists.isPresent()) {
      throw new UserAlreadyRegistered();
    }

    User user = userCreate.toEntity();
    user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
    return this.userRepository.save(user);
  }

  public List<User> findAll() {
    return this.userRepository.findAll();
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

  @Transactional
  public User removeProductsFromCart(Long userId, List<Long> productsIds)
    throws UserNotFoundException, ProductNotFoundException {
    User user = this.findById(userId);
    List<Product> products = user.getCart();

    for (Long id : productsIds) {
      Product product = products
        .stream()
        .filter(p -> Objects.equals(p.getId(), id))
        .findFirst()
        .orElseThrow(() -> new ProductNotFoundException(id));

      products.remove(product);
    }

    return this.userRepository.save(user);
  }

  public void delete(Long id) throws UserNotFoundException {
    User user = this.findById(id);
    this.userRepository.delete(user);
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    return this.userRepository
      .findByEmail(email)
      .orElseThrow(() -> new UsernameNotFoundException("User not found!"));
  }
}
