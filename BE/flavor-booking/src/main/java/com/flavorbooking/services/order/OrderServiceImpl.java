package com.flavorbooking.services.order;

import com.flavorbooking.models.Order;
import com.flavorbooking.repositories.Restaurant.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{
    private final OrdersRepository ordersRepository;
    @Override
    public Page<Order> getAllOrders(Integer pageNo, Integer pageSize, String sortBy, String sortDir, Integer id) {
        try {
            if (sortDir != "None") {
                // Create Sorted instance
                Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                        : Sort.by(sortBy).descending();
                // create Pageable instance
                Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
                Page<Order> ordersPage = ordersRepository.getAllOrders(id, pageable);
                return ordersPage;
            } else {
                Pageable pageable = PageRequest.of(pageNo, pageSize);
                Page<Order> ordersPage = ordersRepository.getAllOrders(id, pageable);
                return ordersPage;
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Page.empty();
        }
    }
}
