package com.walefy.restaurantorders.controller;

import com.walefy.restaurantorders.dto.ProductCreateDto;
import com.walefy.restaurantorders.dto.ProductReturnDto;
import com.walefy.restaurantorders.entity.Product;
import com.walefy.restaurantorders.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {
  private final ProductService productService;

  @Autowired
  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @PostMapping
  public ResponseEntity<ProductReturnDto> create(@RequestBody ProductCreateDto productCreate) {
    Product product = this.productService.create(productCreate);
    return ResponseEntity.status(HttpStatus.CREATED).body(ProductReturnDto.fromEntity(product));
  }
}
