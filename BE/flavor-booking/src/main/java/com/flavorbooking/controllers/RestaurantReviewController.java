package com.flavorbooking.controllers;

import com.flavorbooking.models.RestaurantReview;
import com.flavorbooking.request.RestaurantReviewRequest;
import com.flavorbooking.responses.ResourceResponse;
import com.flavorbooking.services.impl.AccountService;
import com.flavorbooking.services.impl.RestaurantReviewService;
import com.flavorbooking.services.impl.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
public class RestaurantReviewController {

    @Autowired
    RestaurantService restaurantService;
    @Autowired
    AccountService accountService;
    @Autowired
    RestaurantReviewService restaurantReviewService;

    @PostMapping("/add-review/{uid}/{rid}")
    private ResponseEntity<?> addReview(@PathVariable("uid") Integer uid,
                                        @PathVariable("rid") Integer rid,
                                        @RequestBody RestaurantReviewRequest request){
        RestaurantReview review = new RestaurantReview();
        review.setReview(request.getReview());
        review.setRating(request.getRating());
        review.setRestaurant(restaurantService.findResById(rid));
        review.setAccount(accountService.findAccount(uid));
        review.setDate(LocalDate.now());
        ResourceResponse response = new ResourceResponse();
        if(restaurantReviewService.checkReview(rid, uid) == null){
            restaurantReviewService.saveReview(review);
            response.setSuccess(true);
            response.setData(review);
            response.setMessage("ok");
        }
        else{
            response.setSuccess(false);
            response.setData(null);
            response.setMessage("ko");
        }
        return new ResponseEntity<ResourceResponse>(response, HttpStatus.ACCEPTED);
    }

    @GetMapping("/reviews-restaurant/{rid}")
    private ResponseEntity<?> listReview(@PathVariable("rid") Integer rid){
        List<RestaurantReview> reviewList = restaurantReviewService.getReviewByRid(rid);
        ResourceResponse response = new ResourceResponse();
        response.setSuccess(true);
        response.setData(reviewList);
        response.setMessage("ok");
        return new ResponseEntity<ResourceResponse>(response, HttpStatus.ACCEPTED);
    }
}
