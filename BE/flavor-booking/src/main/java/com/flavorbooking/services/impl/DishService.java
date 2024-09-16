package com.flavorbooking.services.impl;

import com.flavorbooking.dtos.DishDTO;
import com.flavorbooking.models.Dish;
import com.flavorbooking.repositories.Restaurant.DishRepository;
import com.flavorbooking.request.CategoryRequest;
import com.flavorbooking.request.RestaurantRequest;
import com.flavorbooking.services.firebase.FirebaseInitialization;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DishService {
    private final FirebaseInitialization firebaseInitialization;
    private final DishRepository dishRepository;
    public Dish saveDish(Dish dish){
      return dishRepository.save(dish);
    }

    public void deleteDish(Integer did){
        dishRepository.deleteById(did);
    }
    public List<Dish> getAllDishAfterDelete(Integer rid){
        return dishRepository.getAllDishByRestaurant(rid);
    }
    public void updateDish(DishDTO dish){
        dishRepository.updateDish(dish.getId(),dish.getTitle(),dish.getImage(),dish.getDescription(),dish.getPrice(),dish.getOldPrice(),
                dish.getProductStatus(),dish.getFeatured(),dish.getUpdated(),dish.getCid());
    }

    public void updateDish2(Dish dish){
        dishRepository.save(dish);
    }

    public List<Dish> getAllDishesByRestaurant(Integer id){
        return dishRepository.findByRestaurant_Id(id);
    }

    public Dish getDish(Integer did){
        return dishRepository.findById(did).orElse(null);
    }

    public List<Dish> getAllDish(){
        return dishRepository.findAll();
    }

    public List<Dish> filterDishbyMax(){
        return dishRepository.findAll();
    }
    public List<Dish> filterProduct(List<CategoryRequest> categoryIds, Double min_price, Double max_price, List<RestaurantRequest> restaurantId){
        return dishRepository.filterProduct(categoryIds,min_price,max_price,restaurantId);
    }
}
