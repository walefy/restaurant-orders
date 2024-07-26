package com.walefy.restaurantorders.service;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.walefy.restaurantorders.dto.UserCreateDto;
import com.walefy.restaurantorders.dto.UserUpdateDto;
import com.walefy.restaurantorders.entity.Product;
import com.walefy.restaurantorders.entity.User;
import com.walefy.restaurantorders.exception.InvalidAdminTokenException;
import com.walefy.restaurantorders.exception.ProductNotFoundException;
import com.walefy.restaurantorders.exception.UserAlreadyRegistered;
import com.walefy.restaurantorders.exception.UserNotFoundException;
import com.walefy.restaurantorders.repository.UserRepository;
import com.walefy.restaurantorders.security.user.Role;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
  private final ObjectMapper objectMapper;

  @Value("${api.security.admin.token}")
  private String adminToken;

  @Autowired
  public UserService(UserRepository userRepository, ProductService productService,
    ObjectMapper objectMapper) {
    this.userRepository = userRepository;
    this.productService = productService;
    this.objectMapper = objectMapper;
  }

  private boolean isAdminUser(User user) {
    return user.getRole().equals(Role.ADMIN);
  }

  private boolean isValidAdminToken(UserCreateDto userCreate, String adminToken) {
    return adminToken.equals(userCreate.adminToken());
  }

  public User create(UserCreateDto userCreate)
    throws UserAlreadyRegistered, InvalidAdminTokenException {
    Optional<User> userExists = this.userRepository.findByEmail(userCreate.email());

    if (userExists.isPresent()) {
      throw new UserAlreadyRegistered();
    }

    User user = userCreate.toEntity();

    if (isAdminUser(user) && !isValidAdminToken(userCreate, adminToken)) {
      throw new InvalidAdminTokenException();
    }

    user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
    return this.userRepository.save(user);
  }

  public List<User> findAll() {
    return this.userRepository.findAll();
  }

  public User findById(Long id) throws UserNotFoundException {
    return this.userRepository.findById(id).orElseThrow(UserNotFoundException::new);
  }

  public User findByEmail(String email) throws UserNotFoundException {
    return this.userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
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
  public User addProductsInOwnCart(String email, List<Long> productsIds)
    throws ProductNotFoundException, UserNotFoundException {
    User user = this.findByEmail(email);
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

  @Transactional
  public User removeProductsFromOwnCart(String email, List<Long> productsIds)
    throws UserNotFoundException, ProductNotFoundException {
    User user = this.findByEmail(email);
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

  public void deleteById(Long id) throws UserNotFoundException {
    User user = this.findById(id);
    this.userRepository.delete(user);
  }

  public void deleteByEmail(String email) throws UserNotFoundException {
    User user = this.findByEmail(email);
    this.userRepository.delete(user);
  }

  @Transactional
  public User update(String email, UserUpdateDto userUpdateData)
    throws UserNotFoundException, JsonMappingException {
    throw new JsonMappingException("");

//    User user = this.findByEmail(email);

//    objectMapper.updateValue(user, userUpdateData);
//    return this.userRepository.save(user);
  }

  @Transactional
  public User update(Long id, UserUpdateDto userUpdateData)
    throws UserNotFoundException, JsonMappingException {
    User user = this.findById(id);

    objectMapper.updateValue(user, userUpdateData);
    return this.userRepository.save(user);
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    return this.userRepository
      .findByEmail(email)
      .orElseThrow(() -> new UsernameNotFoundException("User not found!"));
  }
}
