package com.flavorbooking.controllers;

import com.flavorbooking.dtos.response.ListDishResponse;
import com.flavorbooking.dtos.response.ResponseMessage;
import com.flavorbooking.models.Dish;
import com.flavorbooking.models.Restaurant;
import com.flavorbooking.request.CategoryRequest;
import com.flavorbooking.request.DishRequest;
import com.flavorbooking.services.dish.DishService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api")
public class DishController {
    private final DishService dishService;
    @Autowired
    private com.flavorbooking.services.impl.DishService dishService2;

    @GetMapping(value = "/dishes")
    public ResponseEntity<?> getAllDishes(@RequestParam(defaultValue = "1", required = false, name = "page") Integer page,
                                            @RequestParam(defaultValue = "8", required = false, name = "limit") Integer limit,
                                            @RequestParam(defaultValue = "title", required = false, name = "sortBy") String sortBy,
                                            @RequestParam(defaultValue = "None", required = false, name = "sortDir") String sortDir,
                                            @RequestParam(required = false, name = "id") Integer id,
                                            @RequestParam(required = false, name = "description") String description,
                                            @RequestParam(required = false, name = "price") Double price,
                                            @RequestParam(required = false, name = "featured") Boolean featured,
                                            @RequestParam(required = false, name = "categoryId") Integer categoryId,
                                            @RequestParam(required = false, name = "title") String title,
                                            HttpServletRequest request) {
        try {
            Page<Dish> dishPage = dishService.getAllDishes(page - 1, limit, sortBy, sortDir, id, description, price, featured, categoryId, title);
            if (!dishPage.isEmpty()) {
                return new ResponseEntity<>(new ListDishResponse(0, "Get Dish Successfully", (int) dishPage.getTotalElements(), dishPage.getTotalPages(), dishPage.getContent(), 200), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ListDishResponse(1, "Dish Not Found", 0, 404), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage( e.getMessage(), 400), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/dish")
    public ResponseEntity<?> getAllDishes() {
        List<Dish> data = dishService2.getAllDish();
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "ok");
        response.put("data", data);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/dish/{did}")
    public ResponseEntity<?> getAllDishes(@PathVariable("did") Integer did) {
        Dish data = dishService2.getDish(did);
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "ok");
        response.put("data", data);
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/filter-product")
    public ResponseEntity<?> filterDishes(@RequestBody DishRequest request) {
        List<Dish> data = dishService2.getAllDish();
        List<Dish> results = new ArrayList<>();
        for(int i=0; i< data.size();i++){
            if(data.get(i).getPrice()> request.getMin_price() && data.get(i).getPrice()< request.getMax_price()){
                if(request.getCategories().size()>0){
                    for(int j=0; j<request.getCategories().size();j++){
                        if(request.getCategories().get(j).getCid() == data.get(i).getCategory().getId()){
                            if(request.getRestaurants().size()>0){
                                for(int k = 0; k < request.getRestaurants().size(); k++){
                                    if(data.get(i).getRestaurant().getId() == request.getRestaurants().get(k).getRid()){
                                        results.add(data.get(i));
                                    }
                                }
                            }else{
                                results.add(data.get(i));
                            }
                        }
                    }
                }else {
                    if(request.getRestaurants().size()>0) {
                        for (int k = 0; k < request.getRestaurants().size(); k++) {
                            if (data.get(i).getRestaurant().getId() == request.getRestaurants().get(k).getRid()) {
                                results.add(data.get(i));
                            }
                        }
                    }else{
                        results.add(data.get(i));
                    }
                }
            }
        }
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "ok");
        response.put("data", results);
        return ResponseEntity.ok(response);
    }
//@PostMapping(value = "/filter-product")
//public ResponseEntity<?> filterDishes(@RequestBody DishRequest request) {
//    List<Dish> results = dishService2.filterProduct(request.getCategorys(), request.getMin_price(), request.getMax_price(), request.getRestaurants());
//
//    Map<String, Object> response = new HashMap<>();
//    response.put("success", true);
//    response.put("message", "ok");
//    response.put("data", results);
//    return ResponseEntity.ok(response);
//}
//@PostMapping(value = "/filter-product")
//public ResponseEntity<?> filterDishes(@RequestParam List<CategoryRequest> categorys) {
////    List<Dish> results = dishService2.filterProduct(request.getCategorys(), request.getMin_price(), request.getMax_price(), request.getRestaurants());
//    List<Dish> results = dishService2.filterProduct(categorys, null, null, null);
//    Map<String, Object> response = new HashMap<>();
//    response.put("success", true);
//    response.put("message", "ok");
//    response.put("data", results);
//    return ResponseEntity.ok(response);
//}
}
