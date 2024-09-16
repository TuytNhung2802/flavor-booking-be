package com.flavorbooking.services.impl.admin;

import com.flavorbooking.models.RestaurantWishlist;
import com.flavorbooking.repositories.admin.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishlistService {
    @Autowired
    WishlistRepository wishlistRepository;
    public List<RestaurantWishlist> getAllWishlist(){
        return wishlistRepository.findAll();
    }
    public void addWishlist(RestaurantWishlist wishlist){
        wishlistRepository.save(wishlist);
    }

    public RestaurantWishlist getWishlistbyid(Integer wid ){
        return wishlistRepository.findById(wid).orElse(null);

    }

}
