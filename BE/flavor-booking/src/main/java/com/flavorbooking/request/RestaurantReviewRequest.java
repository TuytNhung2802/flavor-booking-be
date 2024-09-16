package com.flavorbooking.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestaurantReviewRequest {
    private String review;
    private Integer rating;
}
