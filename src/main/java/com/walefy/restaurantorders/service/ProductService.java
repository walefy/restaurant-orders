package com.walefy.restaurantorders.service;

import com.walefy.restaurantorders.dto.ProductCreateDto;
import com.walefy.restaurantorders.entity.Product;
import com.walefy.restaurantorders.repository.ProductRepository;
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
}
