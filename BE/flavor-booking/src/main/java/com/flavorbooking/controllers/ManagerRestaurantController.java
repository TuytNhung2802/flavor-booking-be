package com.flavorbooking.controllers;

import com.flavorbooking.constant.SystemConstant;
import com.flavorbooking.dtos.DishDTO;
import com.flavorbooking.models.*;
import com.flavorbooking.request.RegisterRestaurantRequest;
import com.flavorbooking.responses.ResourceResponse;
import com.flavorbooking.services.firebase.FirebaseInitialization;
import com.flavorbooking.services.impl.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api")
public class ManagerRestaurantController {

    private final RestaurantService restaurantService;
    private final CategoryService categoryService;
    private final DishService dishService;
    private final AccountService accountService;
    private final TableService tableService;
    private final FirebaseInitialization firebaseInitialization;
    private final OrderService orderService;
    private final RoleService roleService;


    @GetMapping(value = "/restaurant")
    public ResponseEntity<?> findAll() {
        List<Restaurant> data = restaurantService.findAll();
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "ok");
        response.put("data", data);
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/register-restaurant/{uid}")
    public ResponseEntity<?> registerRes(@PathVariable("uid") Integer uid,
                                         @RequestBody RegisterRestaurantRequest request) {
        Account account = accountService.findAccount(uid);
        Map<String, Object> response = new HashMap<>();
        if (account != null) {
            //
            account.setRole(roleService.findRole(3));
            accountService.updateRoleAccount(account);
            Restaurant restaurant = new Restaurant();
            restaurant.setLikes(0L);
            restaurant.setAddress(request.getAddress());
            restaurant.setTitle(request.getTitle());
            restaurant.setAccount(account);
            restaurant.setPhone(restaurant.getPhone());
            restaurant.setImage(SystemConstant.RES_DEFAULT);
            restaurant.setEmail(request.getEmail());
            restaurant.setIsActive(false);
            restaurantService.saveRes(restaurant);
            response.put("success", true);
            response.put("message", "cho duyet..");
            response.put("data", null);
        } else {
            response.put("success", false);
            response.put("message", "ko");
            response.put("data", null);
        }
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('RESTAURANT','ADMIN')")
    @PostMapping("/change-restaurant/{rid}")
    public ResponseEntity<?> updateRestaurant(@PathVariable("rid") Integer rid,
                                              @RequestParam(value = "phone", required = false)String phone,
                                              @RequestParam(value = "image",required = false) MultipartFile image,
                                              @RequestParam(value="address",required = false)String address,
                                              @RequestParam("title") String title,
                                              @RequestParam(value="description", required = false)String description,
                                              @RequestParam(value = "time_open", required = false) String time_open,
                                              @RequestParam(value = "time_close", required = false) String time_close) throws IOException {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Map<String, Object> response = new HashMap<>();
        Restaurant restaurant = restaurantService.findResById(rid);
        if(restaurant != null){
            if(image != null) {
                String avatar = firebaseInitialization.uploadFile(image);
                restaurant.setImage(avatar);
            }
            if(description != null) restaurant.setDescription(description);
            if(phone != null) restaurant.setPhone(phone);
            restaurant.setAddress(address);
            restaurant.setTitle(title);
            if(time_close != null) restaurant.setTimeClose(LocalTime.parse(time_close));
            if(time_open != null) restaurant.setTimeOpen(LocalTime.parse(time_open));
            restaurantService.saveRes(restaurant);
            response.put("success", true);
            response.put("message", "ok");
            response.put("data", restaurant);
        } else {
            response.put("success", false);
            response.put("message", "ko");
            response.put("data", null);
        }
        return ResponseEntity.ok(response);
    }


    @GetMapping(value = "/restaurant/{rid}")
    public ResponseEntity<?> findAll(@PathVariable Integer rid) {
        Restaurant data = restaurantService.findResById(rid);
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "ok");
        response.put("data", data);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/statistics/{rid}")
    public ResponseEntity<Object> statistics() {
        try {
            long[] arr = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            List result_1 = orderService.customerOrderTheMost();
            List<Long> result_2 = orderService.numberOfPeopleByMonth();
            if (result_2 != null) {
                for (int i = 0; i < result_2.size(); i++) {
                    arr[i] = result_2.get(i);
                }
            }
            Map<String, Object> response = new HashMap<>();
            Map<String, Object> tamp = new HashMap<>();

            tamp.put("top_user", result_1.get(0));
            tamp.put("num_top_user", result_1.get(1));
            tamp.put("num_order", arr);
            response.put("success", true);
            response.put("message", "Get success");
            response.put("data", tamp);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping(value = "/dishes-of-restaurant/{rid}")
    public ResponseEntity<?> addDish(@PathVariable(value = "rid") Integer rid) {
        try {
            List<Dish> dish = dishService.getAllDishesByRestaurant(rid);
            ResourceResponse response = new ResourceResponse();
            response.setMessage("Add dish successfully");
            response.setSuccess(true);
            response.setData(dish);
            return new ResponseEntity<ResourceResponse>(response, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @PreAuthorize("hasRole('RESTAURANT')")
    @PostMapping(value = "/add-dish/{id}")
    public ResponseEntity<Object> addDish(@PathVariable(value = "id") Integer id,
                                          @RequestParam("title") String title,
                                          @RequestParam("image") MultipartFile image,
                                          @RequestParam("description") String description,
                                          @RequestParam("price") Double price,
                                          @RequestParam("old_price") Double oldPrice,
                                          @RequestParam(value = "product_status", defaultValue = "0") String productStatus,
                                          @RequestParam("cid") Integer cid) {
        try {
            Map<String, Object> response = new HashMap<>();
            Dish dish = new Dish();
            dish.setTitle(title);
            String imageUrl = firebaseInitialization.uploadFile(image);
            dish.setImage(imageUrl);
            LocalDate curent = LocalDate.now();
            dish.setDate(curent);
            dish.setDescription(description);
            dish.setPrice(price);
            dish.setOldPrice(oldPrice);
            dish.setProductStatus(productStatus);

            Restaurant restaurant = restaurantService.findResById(id);
            dish.setRestaurant(restaurant);
            Category category = categoryService.findCateById(cid);
            dish.setCategory(category);
            if (dish != null) {
                dishService.saveDish(dish);
            }
            response.put("success", true);
            response.put("message", "Add dish successfully");
            response.put("data", dish);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @PreAuthorize("hasRole('RESTAURANT')")
    @DeleteMapping(value = "/delete-dish/{rid}/{did}")
    public ResponseEntity<Object> deleteDish(@PathVariable(value = "rid") Integer rid, @PathVariable(value = "did") Integer did) {
        try {
            dishService.deleteDish(did);
            List<Dish> data = dishService.getAllDishAfterDelete(rid);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Add dish successfully");
            response.put("data", data);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @PreAuthorize("hasRole('RESTAURANT')")
    @PutMapping(value = "/update-dish/{did}")
    public ResponseEntity<Object> updateDish(@PathVariable(value = "did") Integer id,
                                             @RequestParam("title") String title,
                                             @RequestParam(value = "image", required = false) MultipartFile image,
                                             @RequestParam("description") String description,
                                             @RequestParam("price") Double price,
                                             @RequestParam("old_price") Double oldPrice,
                                             @RequestParam("featured") Boolean featured,
                                             @RequestParam(value = "product_status", defaultValue = "") String productStatus,
                                             @RequestParam("cid") Integer cid) {
        try {
            Dish dishDTO = dishService.getDish(id);
            if(image != null){
                String imageUrl = firebaseInitialization.uploadFile(image);
                dishDTO.setImage(imageUrl);
            }
            LocalDate curent = LocalDate.now();
            dishDTO.setUpdated(curent);
            dishDTO.setTitle(title);
            dishDTO.setDescription(description);
            dishDTO.setPrice(price);
            dishDTO.setFeatured(featured);
            dishDTO.setOldPrice(oldPrice);
            dishDTO.setProductStatus(productStatus);
            Category category = categoryService.findCateById(cid);
            dishDTO.setCategory(category);
            dishService.updateDish2(dishDTO);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Update dish successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Error updating dish");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PreAuthorize("hasRole('RESTAURANT')")
    @PostMapping(value = "/add-table/{rid}")
    public ResponseEntity<Object> addTable(@PathVariable(value = "rid") Integer rid, @RequestBody RestaurantTable restaurantTable) {
        try {
            if (restaurantTable.getNumberSeat() == null) {
                restaurantTable.setNumberSeat(2);
            }
            Restaurant restaurant = new Restaurant();
            restaurant = restaurantService.findResById(rid);
            restaurantTable.setRestaurant(restaurant);
            tableService.addTable(restaurantTable);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Add table successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/get-table/{rid}")
    public ResponseEntity<Object> getAllTableOfRestaurant(@PathVariable("rid") Integer rid) {
        try {
            List<RestaurantTable> restaurantTables = new ArrayList<>();
            restaurantTables = tableService.getAllTableOfRestaurant(rid);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Get table successfully");
            response.put("data", restaurantTables);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/detail-table/{tid}")
    public ResponseEntity<Object> getDetailTable(@PathVariable("tid") Integer tid) {
        try {
            RestaurantTable restaurantTables = tableService.findTableById(tid);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Get table successfully");
            response.put("data", restaurantTables);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @PreAuthorize("hasRole('RESTAURANT')")
    @PutMapping("update-table/{tid}")
    public ResponseEntity<Object> updateTable(@PathVariable(value = "tid") Integer id, @RequestBody RestaurantTable restaurantTable) {
        try {
            restaurantTable.setId(id);
            tableService.updateTable(restaurantTable);
            Map<String, Object> response = new HashMap<>();
            response.put("success", "true");
            response.put("message", "Update table successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @PreAuthorize("hasRole('RESTAURANT')")
    @DeleteMapping(value = "/delete-table/{rid}/{tid}")
    public ResponseEntity<Object> deleteTableOfRes(@PathVariable(value = "rid") Integer rid, @PathVariable(value = "tid") Integer tid) {
        try {
            tableService.deleteTable(tid);
            List<RestaurantTable> restaurantTables = new ArrayList<>();
            restaurantTables = tableService.getAllTableOfRestaurant(rid);
            Map<String, Object> response = new HashMap<>();
            response.put("succes", true);
            response.put("message", "delete table");
            response.put("data", restaurantTables);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //Category
    @PreAuthorize("hasRole('RESTAURANT')")
    @PostMapping(value = "/add-category/")
    public ResponseEntity<Object> addCategory(@RequestBody Category category) {
        try {
            categoryService.addCategory(category);
            Map<String, Object> response = new HashMap<>();
            response.put("success", "true");
            response.put("message", "Add category successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @PreAuthorize("hasRole('RESTAURANT')")
    @DeleteMapping(value = "/delete-category/{cid}")
    public ResponseEntity<Object> deleteCategory(@PathVariable(value = "cid") Integer cid) {
        try {
            categoryService.deleteCategory(cid);
            Map<String, Object> response = new HashMap<>();
            response.put("success", "true");
            response.put("message", "Delete category successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @PreAuthorize("hasRole('RESTAURANT')")
    @PostMapping(value = "/update-category/{cid}")
    public ResponseEntity<Object> updateCategory(@PathVariable(value = "cid") Integer cid, @RequestBody Category category) {
        try {
            category.setId(cid);
            categoryService.updateCategory(category);
            Map<String, Object> response = new HashMap<>();
            response.put("success", "true");
            response.put("message", "Delete category successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping(value = "/category")
    public ResponseEntity<?> getAllCategory() {
        try {
            List<Category> categoryList = categoryService.getAllCategory();
            ResourceResponse response = new ResourceResponse();
            response.setData(categoryList);
            response.setMessage("ok");
            response.setSuccess(true);
            return new ResponseEntity<ResourceResponse>(response, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
