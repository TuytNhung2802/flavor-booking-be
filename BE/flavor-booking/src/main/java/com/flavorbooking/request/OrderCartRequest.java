package com.flavorbooking.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class OrderCartRequest {
    String full_name, phone, tid, time_from, time_to, number_people, order_date;
    List<ItemDishRequest> items;
}
