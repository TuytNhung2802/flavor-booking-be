package com.flavorbooking.services.impl;

import com.flavorbooking.models.Restaurant;
import com.flavorbooking.repositories.Restaurant.RestaurantRepository;
import com.flavorbooking.services.firebase.FirebaseInitialization;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final FirebaseInitialization firebaseInitialization;

//    public RestaurantService(RestaurantRepository restaurantRepository) {
//        this.restaurantRepository = restaurantRepository;
//    }

    public List<Restaurant> findAll() {
        return restaurantRepository.findByisActive(true);
    }

    public Restaurant findResById(Integer id) {
        return restaurantRepository.findById(id).orElse(null);
    }

    public void increaseLike(Integer rid) {
        Restaurant updateRes = restaurantRepository.findById(rid).orElse(null);
        updateRes.setLikes(updateRes.getLikes() + 1);
        restaurantRepository.save(updateRes);
    }

    public void reducedLike(Integer rid) {
        Restaurant updateRes = restaurantRepository.findById(rid).orElse(null);
        updateRes.setLikes(updateRes.getLikes() - 1);
        restaurantRepository.save(updateRes);
    }

    public Restaurant saveRes(Restaurant restaurant){
        return restaurantRepository.save(restaurant);
    }
}
