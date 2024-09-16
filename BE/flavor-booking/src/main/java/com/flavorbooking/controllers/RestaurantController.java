package com.flavorbooking.controllers;

import com.flavorbooking.services.restaurant.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api")
public class RestaurantController {

    private final RestaurantService restaurantService;

//    @GetMapping(value = "/restaurants")
//    public ResponseEntity<?> getAllBookings(@RequestParam(defaultValue = "1", required = false, name = "page") Integer page,
//                                            @RequestParam(defaultValue = "8", required = false, name = "limit") Integer limit,
//                                            @RequestParam(defaultValue = "title", required = false, name = "sortBy") String sortBy,
//                                            @RequestParam(defaultValue = "None", required = false, name = "sortDir") String sortDir,
//                                            @RequestParam(required = false, name = "id") Integer id,
//                                            @RequestParam(required = false, name = "title") String title,
//                                            @RequestParam(required = false, name = "description") String description
//                                            ) {
//        try {
//            Page<Restaurant> restaurantPage = restaurantService.getAllRestaurants(page - 1, limit, sortBy, sortDir, id, title, description);
//            if (!restaurantPage.isEmpty()) {
//                return new ResponseEntity<>(new ListRestaurantResponse(0, "Get Restaurant Successfully", (int) restaurantPage.getTotalElements(), restaurantPage.getTotalPages(), restaurantPage.getContent(), 200), HttpStatus.OK);
//            } else {
//                return new ResponseEntity<>(new ListRestaurantResponse(1, "Restaurant Not Found", 0, 404), HttpStatus.NOT_FOUND);
//            }
//        } catch (Exception e) {
//            return new ResponseEntity<>(new ResponseMessage(1, e.getMessage(), 400), HttpStatus.BAD_REQUEST);
//        }
//    }

}