package com.walefy.restaurantorders.service;

import com.walefy.restaurantorders.dto.ProductCreateDto;
import com.walefy.restaurantorders.entity.Product;
import com.walefy.restaurantorders.exception.ProductNotFoundException;
import com.walefy.restaurantorders.repository.ProductRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
  private final ProductRepository productRepository;

  @Autowired
  public ProductService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  public Product create(ProductCreateDto productCreate) {
    return this.productRepository.save(productCreate.toEntity());
  }

  public List<Product> findAll() {
    return this.productRepository.findAll();
  }

  public Product findById(Long id) throws ProductNotFoundException {
    return this.productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
  }

  public List<Product> findByName(String name) {
    return this.productRepository.findAllByNameContaining(name);
  }

  public void delete(Long id) throws ProductNotFoundException {
    Product product = this.findById(id);
    this.productRepository.delete(product);
  }
}
