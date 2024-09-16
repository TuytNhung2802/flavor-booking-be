package com.flavorbooking.services.order;

import com.flavorbooking.models.Order;
import org.springframework.data.domain.Page;

public interface OrderService {
    public Page<Order> getAllOrders(Integer pageNo, Integer pageSize, String sortBy, String sortDir, Integer id);

}
