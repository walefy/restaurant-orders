package com.walefy.restaurantorders.controller;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.walefy.restaurantorders.dto.ProductCreateDto;
import com.walefy.restaurantorders.dto.ProductReturnDto;
import com.walefy.restaurantorders.dto.ProductUpdateDto;
import com.walefy.restaurantorders.entity.Product;
import com.walefy.restaurantorders.exception.ProductNotFoundException;
import com.walefy.restaurantorders.service.ProductService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
  @PreAuthorize("hasAuthority('ADMIN')")
  public ResponseEntity<ProductReturnDto> create(@RequestBody @Valid ProductCreateDto productCreate) {
    Product product = this.productService.create(productCreate);
    return ResponseEntity.status(HttpStatus.CREATED).body(ProductReturnDto.fromEntity(product));
  }

  @GetMapping
  public ResponseEntity<List<ProductReturnDto>> findAll(
    @RequestParam(required = false) String name
  ) {
    List<Product> products = name != null
      ? this.productService.findByName(name)
      : this.productService.findAll();

    List<ProductReturnDto> productsReturn = products
      .stream()
      .map(ProductReturnDto::fromEntity)
      .toList();

    return ResponseEntity.status(HttpStatus.OK).body(productsReturn);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ProductReturnDto> findById(@PathVariable Long id)
    throws ProductNotFoundException {
    Product product = this.productService.findById(id);
    return ResponseEntity.status(HttpStatus.OK).body(ProductReturnDto.fromEntity(product));
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasAuthority('ADMIN')")
  public ResponseEntity<Void> deleteById(@PathVariable Long id) throws ProductNotFoundException {
    this.productService.delete(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
  }

  @PatchMapping("/{id}")
  @PreAuthorize("hasAuthority('ADMIN')")
  public ResponseEntity<ProductReturnDto> update(@RequestBody @Valid ProductUpdateDto productUpdate, @PathVariable Long id)
    throws JsonMappingException, ProductNotFoundException {
    Product product = this.productService.update(id, productUpdate);

    return ResponseEntity.status(HttpStatus.OK).body(ProductReturnDto.fromEntity(product));
  }
}
