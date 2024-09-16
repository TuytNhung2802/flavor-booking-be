package com.flavorbooking.repositories.Restaurant;

import com.flavorbooking.models.OrderCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderCartRepository extends JpaRepository<OrderCart, Integer> {

    @Query(nativeQuery = true, value = "select id from order_cart where restaurant_id=:restaurant_id and account_id=:account_id")
    public Integer getOrderCartByUidAndRid(@Param("account_id") Integer account_id, @Param("restaurant_id") Integer restaurant_id);
}
