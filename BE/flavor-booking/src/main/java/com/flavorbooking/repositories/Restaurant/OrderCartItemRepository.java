package com.flavorbooking.repositories.Restaurant;

import com.flavorbooking.models.OrderCartDish;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderCartItemRepository extends JpaRepository<OrderCartDish, Integer> {

    @Query(nativeQuery = true, value = "select * from order_cart_dish where order_cart_id=:oid")
    public List<OrderCartDish> getAllCart(@Param("oid") Integer oid);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "delete from order_cart_dish where order_cart_id=:oid")
    public void deleleCartByRid(@Param("oid") Integer oid);
}
