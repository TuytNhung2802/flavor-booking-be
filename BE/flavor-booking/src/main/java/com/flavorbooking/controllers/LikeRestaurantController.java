package com.flavorbooking.controllers;

import com.flavorbooking.models.RestaurantWishlist;
import com.flavorbooking.responses.ResourceResponse;
import com.flavorbooking.services.impl.AccountService;
import com.flavorbooking.services.impl.LikeRestaurantService;
import com.flavorbooking.services.impl.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
public class LikeRestaurantController {
    @Autowired
    LikeRestaurantService likeRestaurantService;
    @Autowired
    RestaurantService restaurantService;
    @Autowired
    AccountService accountService;

    @GetMapping("/list-like/{uid}")
    private ResponseEntity<?> listLike(@PathVariable("uid") Integer uid){
        ResourceResponse response = new ResourceResponse();
        List<RestaurantWishlist> data = likeRestaurantService.getAllByUid(uid);
        response.setData(data);
        response.setSuccess(true);
        response.setMessage("Get successfully!");
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @GetMapping("/add-like/{uid}/{rid}")
    private ResponseEntity<?> addLike(@PathVariable("uid") Integer uid, @PathVariable("rid") Integer rid){
        Integer lid = likeRestaurantService.checkLike(uid, rid);
        ResourceResponse response = new ResourceResponse();
        if(lid == null){
            restaurantService.increaseLike(rid);
            RestaurantWishlist wishlist = new RestaurantWishlist();
            wishlist.setDate(LocalDate.now());
            wishlist.setRestaurant(restaurantService.findResById(rid));
            wishlist.setAccount(accountService.findAccount(uid));
            likeRestaurantService.saveLike(wishlist);
            response.setData(likeRestaurantService.getAllByUid(uid));
            response.setSuccess(true);
            response.setMessage("Add successfully!");
        }else {
            response.setData(null);
            response.setSuccess(false);
            response.setMessage("Added previously!");
        }
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete-like/{uid}/{rid}")
    private ResponseEntity<?> deleteLike(@PathVariable("uid") Integer uid, @PathVariable("rid") Integer rid){
        Integer lid = likeRestaurantService.checkLike(uid, rid);
        ResourceResponse response = new ResourceResponse();
        if(lid != null){
            restaurantService.reducedLike(rid);
            likeRestaurantService.deleteLike(lid);
            List<RestaurantWishlist> data = likeRestaurantService.getAllByUid(uid);
            response.setData(data);
            response.setSuccess(true);
            response.setMessage("Delete successfully!");
        }else {
            response.setData(null);
            response.setSuccess(false);
            response.setMessage("Not exist!");
        }
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }
}
