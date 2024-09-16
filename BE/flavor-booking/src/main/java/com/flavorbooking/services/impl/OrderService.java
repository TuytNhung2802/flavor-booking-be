package com.flavorbooking.services.impl;

import com.flavorbooking.models.Order;
import com.flavorbooking.repositories.Restaurant.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderService {
    private  final OrdersRepository ordersRepository;

    public List<Object> customerOrderTheMost() {

        List result = new ArrayList();
        List<String> str = new ArrayList<>();
        List<Long> log = new ArrayList<>();
        List<Object[]> results = ordersRepository.customerOrderTheMost();
        for (Object[] row : results) {
            str.add((String) row[0]);
            log.add((Long) row[1]);
        }
        result.add(str);
        result.add(log);
        return result;
    }

    public List<Long> numberOfPeopleByMonth() {
       List<Long> ar = new ArrayList<>();
        List<Object[]> results = ordersRepository.numberOfPeopleByMonth();
        for (Object[] row : results) {
            Long count = (Long) row[0];
            ar.add(count);
        }
        return ar;
    }

    public Order addOrder(Order order){
        Order newOrder = ordersRepository.save(order);
        return newOrder;
    }

    public List<Object[]> getOrderByDate(String dateOrder, int tid){
        List<Object[]> newOrder = ordersRepository.getOrderByDate(dateOrder, tid);
        return newOrder;
    }

    public List<Order> getOrderCurrentDate(String date, int rid){
        List<Order> currentOrder = ordersRepository.getOrderCurrentDate(date, rid);
        return currentOrder;
    }

    public List<Order> getOrderByUid(Integer uid){
        List<Order> currentOrder = ordersRepository.getOrderByUid(uid);
        return currentOrder;
    }

    public List<Order> getOrderByRid(Integer rid){
        List<Order> currentOrder = ordersRepository.getOrderByRid(rid);
        return currentOrder;
    }

    public void cancelOrder(Integer oid){
        Order updateOrder = ordersRepository.findById(oid).orElse(null);
        updateOrder.setProductStatus(3);
        ordersRepository.save(updateOrder);
    }

    public void updateStatusOrder(Integer oid, Integer status){
        Order updateOrder = ordersRepository.findById(oid).orElse(null);
        updateOrder.setProductStatus(status);
        ordersRepository.save(updateOrder);
    }

    public List<Order> getOrderCurrent(){
        return ordersRepository.findAll();
    }
    public Order saveOrder(Order order){
        return ordersRepository.save(order);
    }

    public Order findOrderById(int oid){
        return ordersRepository.findById(oid).orElse(null);
    }
}
