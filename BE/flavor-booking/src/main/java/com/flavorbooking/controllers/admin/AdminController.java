package com.flavorbooking.controllers.admin;

import com.flavorbooking.dtos.UserDTO;
import com.flavorbooking.dtos.response.ResponseMessage;
import com.flavorbooking.models.*;
import com.flavorbooking.request.WishlistRequest;
import com.flavorbooking.responses.ResourceResponse;
import com.flavorbooking.services.impl.AccountService;
import com.flavorbooking.services.impl.admin.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
public class AdminController {
    // be
    public final AdminService adminService;
    @Autowired
    private WishlistService wishlistService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private RestaurantsService restaurantsService;
    @Autowired
    private CountClientService transactionService;
    @Autowired
    private SupportMessageSerice supportmessageSerice;
    @Autowired
    private InvoiceQuanlitySerice invoicequanlitySerice;

    @GetMapping(value = "/wishlist")
    public ResponseEntity<?> getWishlist(){
        ResourceResponse response =new ResourceResponse<>();
        response.setSuccess(true);
        response.setMessage("get");
        response.setData(wishlistService.getAllWishlist());
        return new ResponseEntity<ResourceResponse>(response, HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "/wishlist/{wid}")
    public ResponseEntity<?> getWishlistDetail(@PathVariable("wid") Integer wid){
        ResourceResponse response =new ResourceResponse<>();
        response.setSuccess(true);
        response.setMessage("get");
        response.setData(wishlistService.getWishlistbyid(wid));
        return new ResponseEntity<ResourceResponse>(response, HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "/restaurant-admin")
    public ResponseEntity<?> findRestaurants(){
        ResourceResponse response= new ResourceResponse<>();
        response.setSuccess(true);
        response.setMessage("get");
        response.setData(restaurantsService.getAllRestaurants());
        return new ResponseEntity<ResourceResponse>(response, HttpStatus.ACCEPTED);
    }

    @GetMapping(value="/countclient")
    public ResponseEntity<?> sumAllCountClient(){
        ResourceResponse response= new ResourceResponse<>();
        response.setSuccess(true);
        response.setMessage("get");
        response.setData(transactionService.sumAllCountClient());
        return new ResponseEntity<ResourceResponse>(response, HttpStatus.ACCEPTED);
    }

    @GetMapping(value="/countrestaurant")
    public ResponseEntity<?> sumAllCountRestaurant(){
        ResourceResponse response= new ResourceResponse<>();
        response.setSuccess(true);
        response.setMessage("get");
        response.setData(restaurantsService.sumAllCountRestaurant());
        return new ResponseEntity<ResourceResponse>(response, HttpStatus.ACCEPTED);
    }


    @GetMapping(value="/supportmessage")
    public ResponseEntity<?> sumAllSupportMessage(){
        ResourceResponse response= new ResourceResponse<>();
        response.setSuccess(true);
        response.setMessage("get");
        response.setData(supportmessageSerice.sumAllSupportMessage());
        return new ResponseEntity<ResourceResponse>(response, HttpStatus.ACCEPTED);
    }


    @GetMapping(value="/invoicequanlity")
    public ResponseEntity<?> sumAllInvoiceQuanlity(){
        ResourceResponse response= new ResourceResponse<>();
        response.setSuccess(true);
        response.setMessage("get");
        response.setData(invoicequanlitySerice.sumAllInvoiceQuanlity());
        return new ResponseEntity<ResourceResponse>(response, HttpStatus.ACCEPTED);
    }

    @PostMapping (value="/add-wishlist")
    public ResponseEntity<?> addWishlist(@RequestBody WishlistRequest request){
        RestaurantWishlist wishlist = new RestaurantWishlist();
        wishlist.setDate(LocalDate.now());
        Account account = accountService.findAccount(request.getUid());
        wishlist.setAccount(account);
        Restaurant restaurant = restaurantsService.findResbyId(request.getRid());
        wishlist.setRestaurant(restaurant);
        wishlistService.addWishlist(wishlist);
        ResourceResponse response= new ResourceResponse<>();
        response.setSuccess(true);
        response.setMessage("add");
        response.setData(wishlist);
        return new ResponseEntity<ResourceResponse>(response, HttpStatus.ACCEPTED);
    }

    @PutMapping (value="/update-wishlist/{wid}")
    public ResponseEntity<?> updateWishlist(@RequestBody WishlistRequest request, @PathVariable("wid") Integer wid){
        RestaurantWishlist wishlist = wishlistService.getWishlistbyid(wid);
        wishlist.setDate(LocalDate.now());
        Account account = accountService.findAccount(request.getUid());
        wishlist.setAccount(account);
        Restaurant restaurant = restaurantsService.findResbyId(request.getRid());
        wishlist.setRestaurant(restaurant);
        wishlistService.addWishlist(wishlist);
        ResourceResponse response= new ResourceResponse<>();
        response.setSuccess(true);
        response.setMessage("add");
        response.setData(wishlist);
        return new ResponseEntity<ResourceResponse>(response, HttpStatus.ACCEPTED);
    }

    @PutMapping (value="/approve-restaurant/{rid}")
    public ResponseEntity<?> approveRes(@RequestParam("active") boolean active, @PathVariable("rid") Integer rid){
        Restaurant restaurant = restaurantsService.findResbyId(rid);
        System.out.println(active);
        restaurant.setIsActive(active);
        restaurantsService.saveRestaurants(restaurant);
        ResourceResponse response= new ResourceResponse<>();
        response.setSuccess(true);
        response.setMessage("add");
        response.setData(restaurant);
        return new ResponseEntity<ResourceResponse>(response, HttpStatus.ACCEPTED);
    }

    @GetMapping("/user-list/{id}")
    public ResponseEntity<ResourceResponse<Account>> getUserAccount(@PathVariable Integer id){
        Account userAccount =  adminService.getUserAccount(id);
        return ResponseEntity.ok(ResourceResponse.<Account>builder().success(true).message("passed Test").data(userAccount).build());
    }
    @DeleteMapping("/delete-user/{id}")

    public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
        adminService.deletebyId(id);
        return ResponseEntity.ok(ResourceResponse.builder()
                .message("delete successfully")
                .success(true)
                .build());
    }

    @GetMapping(value = "/user-admin")
    public ResponseEntity<?> findUser(){
        ResourceResponse response= new ResourceResponse<>();
        response.setSuccess(true);
        response.setMessage("get");
        response.setData(accountService.getAllAccount());
        return new ResponseEntity<ResourceResponse>(response, HttpStatus.ACCEPTED);
    }

    @PutMapping("/user/update/{id}")
    public ResponseEntity<ResponseMessage> updateAccount(@PathVariable Integer id, @RequestBody UserDTO req) {
        ResponseMessage userAccount = adminService.updateUser(id, req);
        return ResponseEntity.ok(userAccount);
    }
}
