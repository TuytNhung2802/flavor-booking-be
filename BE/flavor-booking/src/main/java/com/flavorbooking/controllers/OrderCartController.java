package com.flavorbooking.controllers;

import com.flavorbooking.dtos.OrderCartDTO;
import com.flavorbooking.models.*;
import com.flavorbooking.request.ItemDishRequest;
import com.flavorbooking.request.OrderCartRequest;
import com.flavorbooking.responses.ResourceResponse;
import com.flavorbooking.services.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
@RequestMapping("/api")
public class OrderCartController {

    @Autowired
    private OrderCartService orderCartService;
    @Autowired
    RestaurantTableService restaurantTableService;
    @Autowired
    RestaurantService restaurantService;
    @Autowired
    AccountService customerService;
    @Autowired
    DishService dishService;
    @Autowired
    OrderCartItemService orderCartItemService;

    @PostMapping("add-order-cart/{uid}/{rid}")
    private ResponseEntity<?> addOrderCart(@RequestBody OrderCartRequest request, @PathVariable int uid, @PathVariable int rid){
        OrderCart orderCart = new OrderCart();
        orderCart.setFullName(request.getFull_name());
        orderCart.setPhone(request.getPhone());
        orderCart.setTimeFrom(LocalTime.parse(request.getTime_from()));
        orderCart.setTimeTo(LocalTime.parse(request.getTime_to()));
        RestaurantTable table = restaurantTableService.getTable(Integer.parseInt(request.getTid()));
        orderCart.setRestaurantTable(table);
        orderCart.setNumberPeople(Integer.parseInt(request.getNumber_people()));
        orderCart.setOrderDate(LocalDate.parse(request.getOrder_date()));
        Restaurant restaurant = restaurantService.findResById(rid);
        orderCart.setRestaurant(restaurant);
        Account account = customerService.findAccount(uid);
        orderCart.setAccount(account);

        orderCartService.addOrder(orderCart);
        ResourceResponse response = new ResourceResponse();
        response.setSuccess(true);
        response.setMessage("ok");
        response.setData(orderCart);

        for(int i=0; i < request.getItems().size(); i++){
            OrderCartDish orderItem = new OrderCartDish();
            orderItem.setQuatity(Integer.parseInt(request.getItems().get(i).getQuantity()));
            Dish dish = dishService.getDish(Integer.parseInt(request.getItems().get(i).getDid()));
            orderItem.setDish(dish);
            orderItem.setOrderCart(orderCart);
            orderCartItemService.addOrderCartItem(orderItem);
        }

        return new ResponseEntity<ResourceResponse>(response, HttpStatus.ACCEPTED);
    }

    @GetMapping("order-cart/{uid}/{rid}")
    private ResponseEntity<?> getOrderCart(@PathVariable Integer uid, @PathVariable Integer rid){
        Integer idOrderCart = orderCartService.getIdByUidAndRid(uid, rid);
        ResourceResponse response = new ResourceResponse();
        if(idOrderCart != null){
            OrderCart orderCart = orderCartService.getOrderCart(idOrderCart);
            List<OrderCartDish> orderCartDishList = orderCartItemService.getAllCartDish(idOrderCart);
            OrderCartDTO orderCartDTO = new OrderCartDTO(orderCart, orderCartDishList);
            response.setSuccess(true);
            response.setMessage("ok");
            response.setData(orderCartDTO);
        }else{
            response.setSuccess(false);
            response.setMessage("ko");
            response.setData(null);
        }
        return new ResponseEntity<ResourceResponse>(response, HttpStatus.ACCEPTED);
    }

    @PostMapping("update-order-cart/{uid}/{rid}")
    private ResponseEntity<?> updateOrderCart(@RequestBody OrderCartRequest request, @PathVariable Integer uid, @PathVariable Integer rid) {
        Integer idOrderCart = orderCartService.getIdByUidAndRid(uid, rid);

        if (idOrderCart != null) {
            OrderCart orderCart = orderCartService.getOrderCart(idOrderCart);
            if (orderCart != null) {
                orderCart.setFullName(request.getFull_name());
                orderCart.setPhone(request.getPhone());
                orderCart.setTimeFrom(LocalTime.parse(request.getTime_from()));
                orderCart.setTimeTo(LocalTime.parse(request.getTime_to()));
                RestaurantTable table = restaurantTableService.getTable(Integer.parseInt(request.getTid()));
                orderCart.setRestaurantTable(table);
                orderCart.setOrderDate(LocalDate.parse(request.getOrder_date()));
                orderCart.setNumberPeople(Integer.parseInt(request.getNumber_people()));

                orderCartService.updateOrder(orderCart, idOrderCart);

                orderCartItemService.deleteOrderCart(idOrderCart);

                for (ItemDishRequest itemRequest : request.getItems()) {
                    OrderCartDish orderItem = new OrderCartDish();
                    orderItem.setQuatity(Integer.parseInt(itemRequest.getQuantity()));
                    Dish dish = dishService.getDish(Integer.parseInt(itemRequest.getDid()));
                    orderItem.setDish(dish);
                    orderItem.setOrderCart(orderCart);
                    orderCartItemService.addOrderCartItem(orderItem);
                }

                ResourceResponse response = new ResourceResponse();
                response.setSuccess(true);
                response.setMessage("ok");
                response.setData(orderCart);

                return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
            } else {
                return new ResponseEntity<>("OrderCart not found", HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>("OrderCart ID not found", HttpStatus.NOT_FOUND);
        }
    }

}
