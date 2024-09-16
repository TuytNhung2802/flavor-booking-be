package com.flavorbooking.repositories.Restaurant;

import com.flavorbooking.models.OrderCartDish;
import com.flavorbooking.models.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {

    @Query(nativeQuery = true, value = "select * from order_item where order_id=:oid")
    public List<OrderItem> getAllOrder(@Param("oid") Integer oid);

    @Query(nativeQuery = true, value = "select * from order_item where order_id=:oid and dish_id=:did")
    public OrderItem getOrderItemByOidAndDid(@Param("oid") Integer oid, @Param("did") Integer did);
}