package com.walefy.restaurantorders.repository;

import com.walefy.restaurantorders.entity.Product;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
  List<Product> findAllByName(String name);
}
