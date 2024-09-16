package com.flavorbooking.services.impl;

import com.flavorbooking.models.OrderCart;
import com.flavorbooking.repositories.Restaurant.OrderCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderCartService {
    @Autowired
    private OrderCartRepository orderCartRepository;

    public OrderCart addOrder(OrderCart order){
        OrderCart newOrder = orderCartRepository.save(order);
        return newOrder;
    }
    public OrderCart getOrder(Integer id){
        OrderCart newOrder = orderCartRepository.findById(id).orElse(null);
        return newOrder;
    }

    public Integer getIdByUidAndRid(Integer uid, Integer rid){
        return orderCartRepository.getOrderCartByUidAndRid(uid, rid);
    }

    public OrderCart getOrderCart(Integer oid){
        return orderCartRepository.findById(oid).orElse(null);
    }

    public OrderCart updateOrder(OrderCart order, Integer oid){
        OrderCart orderCart = orderCartRepository.findById(oid).orElse(null);
        orderCart.setFullName(order.getFullName());
        orderCart.setPhone(order.getPhone());
        orderCart.setTimeFrom(order.getTimeFrom());
        orderCart.setTimeTo(order.getTimeTo());
        orderCart.setRestaurantTable(order.getRestaurantTable());
        orderCart.setNumberPeople(order.getNumberPeople());
        orderCart.setOrderDate(order.getOrderDate());
        orderCart.setRestaurant(order.getRestaurant());
        orderCart.setAccount(order.getAccount());
        return orderCartRepository.save(orderCart);
    }
}
