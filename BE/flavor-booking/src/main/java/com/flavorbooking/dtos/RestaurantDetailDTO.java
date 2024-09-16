package com.flavorbooking.dtos;

import com.flavorbooking.models.Restaurant;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestaurantDetailDTO {
    private String message;
    private boolean success;
    Restaurant data;

    public RestaurantDetailDTO(String message, boolean success, Restaurant data) {
        this.message = message;
        this.success = success;
        this.data = data;
    }
}
