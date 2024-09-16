package com.flavorbooking.services.dish;

import com.flavorbooking.models.Dish;
import org.springframework.data.domain.Page;

public interface DishService {
    Page<Dish> getAllDishes(Integer pageNo, Integer pageSize, String sortBy, String sortDir, Integer id, String description, Double price, Boolean featured, Integer categoryId, String title);
}
