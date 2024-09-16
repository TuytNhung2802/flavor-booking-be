package com.flavorbooking.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemDishRequest {
    String did, quantity, image;
    double price, total;
}
