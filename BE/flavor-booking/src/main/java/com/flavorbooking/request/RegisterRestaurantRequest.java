package com.flavorbooking.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRestaurantRequest {
    private String email, address, title, phone;
}
