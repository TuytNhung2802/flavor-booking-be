package com.flavorbooking.services.impl.admin;

import com.flavorbooking.models.Restaurant;
import com.flavorbooking.models.RestaurantWishlist;
import com.flavorbooking.repositories.admin.RestaurantsRepository;
import com.flavorbooking.repositories.admin.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RestaurantsService {
    @Autowired
    RestaurantsRepository restaurantsRepository;
    public int sumAllCountRestaurant(){
        return restaurantsRepository.sumAllCountRestaurant();
    }

    public List<Restaurant> getAllRestaurants(){
        return restaurantsRepository.findAll();
    }

    public Restaurant findResbyId(Integer rid){
        return restaurantsRepository.findById(rid).orElse(null);
    }

    public void saveRestaurants( Restaurant restaurant){
        restaurantsRepository.save(restaurant);
    }

}
