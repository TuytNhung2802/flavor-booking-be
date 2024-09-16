package com.flavorbooking.dtos.response;

import com.flavorbooking.models.Restaurant;

import java.util.List;

public class ListRestaurantResponse {
    private Integer error;
    private String message;
    private Integer RestaurantQuantity;
    private Integer totalPages;
    private List<Restaurant> listRestaurants;
    private Integer status;

    public ListRestaurantResponse() {
    }

    public ListRestaurantResponse(Integer error, String message, Integer restaurantQuantity, Integer status) {
        this.error = error;
        this.message = message;
        this.RestaurantQuantity = restaurantQuantity;
        this.status = status;
    }

    public ListRestaurantResponse(Integer error, String message, Integer restaurantQuantity, List<Restaurant> listRestaurants, Integer status) {
        this.error = error;
        this.message = message;
        this.RestaurantQuantity = restaurantQuantity;
        this.listRestaurants = listRestaurants;
        this.status = status;
    }

    public ListRestaurantResponse(Integer error, String message, Integer restaurantQuantity, Integer totalPages, List<Restaurant> listRestaurants, Integer status) {
        this.error = error;
        this.message = message;
        this.RestaurantQuantity = restaurantQuantity;
        this.totalPages = totalPages;
        this.listRestaurants = listRestaurants;
        this.status = status;
    }

    public Integer getError() {
        return error;
    }

    public void setError(Integer error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getRestaurantQuantity() {
        return RestaurantQuantity;
    }

    public void setRestaurantQuantity(Integer restaurantQuantity) {
        RestaurantQuantity = restaurantQuantity;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public List<Restaurant> getListRestaurants() {
        return listRestaurants;
    }

    public void setListRestaurants(List<Restaurant> listRestaurants) {
        this.listRestaurants = listRestaurants;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
