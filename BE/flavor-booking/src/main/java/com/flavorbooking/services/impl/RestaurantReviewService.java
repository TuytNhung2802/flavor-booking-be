package com.flavorbooking.services.impl;

import com.flavorbooking.models.RestaurantReview;
import com.flavorbooking.repositories.Restaurant.RestaurantReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantReviewService {
    @Autowired
    RestaurantReviewRepository restaurantReviewRepository;

    public void saveReview(RestaurantReview review){
        restaurantReviewRepository.save(review);
    }

    public List<RestaurantReview> getReviewByRid(Integer rid){
        return restaurantReviewRepository.getReviewByRid(rid);
    }

    public Integer checkReview(Integer rid, Integer uid){
        return restaurantReviewRepository.checkReview(rid, uid);
    }
}
