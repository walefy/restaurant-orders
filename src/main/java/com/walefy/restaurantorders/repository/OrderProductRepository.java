package com.walefy.restaurantorders.repository;

import com.walefy.restaurantorders.entity.OrderProduct;
import com.walefy.restaurantorders.entity.OrderProductId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, OrderProductId> {
}
