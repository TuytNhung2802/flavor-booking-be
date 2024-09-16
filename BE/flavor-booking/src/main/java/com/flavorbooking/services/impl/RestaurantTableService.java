package com.flavorbooking.services.impl;

import com.flavorbooking.models.RestaurantTable;
import com.flavorbooking.repositories.Restaurant.RestaurantTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RestaurantTableService {
    @Autowired
    private RestaurantTableRepository restaurantTableRepository;

    public RestaurantTable getTable(Integer id) {
        return restaurantTableRepository.findById(id).orElse(null);
    }
}
