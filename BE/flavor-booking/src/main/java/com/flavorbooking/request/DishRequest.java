package com.flavorbooking.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DishRequest {
    private double max_price, min_price;
    List<CategoryRequest> categories;
    List<RestaurantRequest> restaurants;
}
