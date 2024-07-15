package com.walefy.restaurantorders.dto;

import java.util.List;

public record OrderCreateDto(Long userId, List<Long> productsIds) {
}
