package com.flavorbooking.services.impl;

import com.flavorbooking.models.RestaurantWishlist;
import com.flavorbooking.repositories.Restaurant.LikeRestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikeRestaurantService {
    @Autowired
    LikeRestaurantRepository likeRestaurantRepository;

    public void saveLike(RestaurantWishlist wishlist){
        likeRestaurantRepository.save(wishlist);
    }

    public List<RestaurantWishlist> getAllByUid(Integer uid){
        return likeRestaurantRepository.findAllbyUid(uid);
    }

    public Integer checkLike(Integer uid, Integer rid){
        return likeRestaurantRepository.checkLike(uid, rid);
    }

    public void deleteLike(Integer id){
        likeRestaurantRepository.delete(likeRestaurantRepository.findById(id).orElse(null));
    }
}
