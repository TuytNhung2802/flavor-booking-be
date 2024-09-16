package com.flavorbooking.services.restaurant;

import com.flavorbooking.dtos.restaurant.RestaurantRegisterDTO;
import com.flavorbooking.models.Restaurant;
import com.flavorbooking.responses.auth.MessageResponse;
import org.springframework.data.domain.Page;

public interface RestaurantService {
    public Page<Restaurant> getAllRestaurants(Integer pageNo, Integer pageSize, String sortBy, String sortDir, Integer id, String title, String description);
    MessageResponse registerRestaurant(RestaurantRegisterDTO request);
}
