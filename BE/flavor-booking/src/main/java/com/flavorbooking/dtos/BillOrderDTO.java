package com.flavorbooking.dtos;

import com.flavorbooking.models.Order;
import com.flavorbooking.models.OrderItem;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BillOrderDTO {
    Order order;
    List<OrderItem> orderItems;

    public BillOrderDTO(Order order, List<OrderItem> orderItems) {
        this.order = order;
        this.orderItems = orderItems;
    }
}
