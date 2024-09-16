package com.flavorbooking.services.impl;

import com.flavorbooking.models.OrderCartDish;
import com.flavorbooking.repositories.Restaurant.OrderCartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderCartItemService {
    @Autowired
    private OrderCartItemRepository orderCartItemRepository;

    public OrderCartDish addOrderCartItem(OrderCartDish order){
        return orderCartItemRepository.save(order);
    }

    public List<OrderCartDish> getAllCartDish(Integer oid){
        return orderCartItemRepository.getAllCart(oid);
    }

    public void deleteOrderCart(Integer oid){
        orderCartItemRepository.deleleCartByRid(oid);
    }
}
