package com.flavorbooking.dtos.response;

import com.flavorbooking.models.Dish;
import com.flavorbooking.models.Restaurant;

import java.util.List;

public class ListDishResponse {
    private Integer error;
    private String message;
    private Integer DishQuantity;
    private Integer totalPages;
    private List<Dish> listDish;
    private Integer status;

    public ListDishResponse() {
    }

    public ListDishResponse(Integer error, String message, Integer dishQuantity, Integer totalPages, List<Dish> listDish, Integer status) {
        this.error = error;
        this.message = message;
        DishQuantity = dishQuantity;
        this.totalPages = totalPages;
        this.listDish = listDish;
        this.status = status;
    }

    public ListDishResponse(Integer error, String message, Integer dishQuantity, Integer status) {
        this.error = error;
        this.message = message;
        DishQuantity = dishQuantity;
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

    public Integer getDishQuantity() {
        return DishQuantity;
    }

    public void setDishQuantity(Integer dishQuantity) {
        DishQuantity = dishQuantity;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public List<Dish> getListDish() {
        return listDish;
    }

    public void setListDish(List<Dish> listDish) {
        this.listDish = listDish;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
