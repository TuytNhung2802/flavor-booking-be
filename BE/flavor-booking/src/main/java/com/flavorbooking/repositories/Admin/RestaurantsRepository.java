package com.flavorbooking.repositories.admin;

import com.flavorbooking.models.Restaurant;
import com.flavorbooking.models.RestaurantWishlist;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RestaurantsRepository extends JpaRepository<Restaurant,Integer> {
    @Query(value = "select * from restaurant ",nativeQuery = true)
    List<RestaurantWishlist> getAllTableOfRes();

    @Modifying
    @Transactional
    @Query(value = "update tables set title = :titles, number_seat = :numbers ",nativeQuery = true)
    void  updateTable(@Param(value = "titles")String titles, @Param(value = "numbers")Integer numbers);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "select sum(restaurant_id) as total_restaurant_id from RestaurantTable ")
    public int sumAllCountRestaurant();
}


