package com.flavorbooking.dtos;

import com.flavorbooking.models.OrderCart;
import com.flavorbooking.models.OrderCartDish;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderCartDTO {
    OrderCart order;
    List<OrderCartDish> orderDetail;

    public OrderCartDTO(OrderCart order, List<OrderCartDish> orderDetail) {
        this.order = order;
        this.orderDetail = orderDetail;
    }
}
