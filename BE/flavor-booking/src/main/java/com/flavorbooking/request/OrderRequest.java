package com.flavorbooking.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderRequest {
    String full_name, phone, tid, time_from, time_to, number_people, order_date;
    double deposit, price;
    List<ItemDishRequest> items;
}