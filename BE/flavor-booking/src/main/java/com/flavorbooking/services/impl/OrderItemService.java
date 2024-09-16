package com.flavorbooking.services.impl;

import com.flavorbooking.models.OrderCartDish;
import com.flavorbooking.models.OrderItem;
import com.flavorbooking.repositories.Restaurant.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemService {
    @Autowired
    private OrderItemRepository orderItemRepository;

    public OrderItem addOrderItem(OrderItem orderItem){
        return orderItemRepository.save(orderItem);
    }

    public List<OrderItem> getAllOrder(Integer oid){
        return orderItemRepository.getAllOrder(oid);
    }

    public List<OrderItem> updateOrderItem(Integer oid, Integer did, Integer quantity){
        OrderItem orderItem = orderItemRepository.getOrderItemByOidAndDid(oid, did);
        orderItem.setQuatity(orderItem.getQuatity()+quantity);
        orderItemRepository.save(orderItem);
        return orderItemRepository.getAllOrder(oid);
    }

    public OrderItem getOrderItemByDidAndOid(Integer oid, Integer did){
        return orderItemRepository.getOrderItemByOidAndDid(oid, did);
    }

    public void deleteOrderItem(Integer oiid){
        System.out.println(oiid);
        orderItemRepository.deleteById(oiid);
    }
}
