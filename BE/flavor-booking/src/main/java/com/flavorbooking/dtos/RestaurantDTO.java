package com.flavorbooking.dtos;

import com.flavorbooking.models.Restaurant;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class RestaurantDTO {
    private String message;
    private boolean success;
    List<Restaurant> data;

    public RestaurantDTO(String message, boolean success, List<Restaurant> data) {
        this.message = message;
        this.success = success;
        this.data = data;
    }

}
