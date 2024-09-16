package com.flavorbooking.repositories.Restaurant;

import com.flavorbooking.models.RestaurantTable;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TableRepository extends JpaRepository<RestaurantTable,Integer> {

    @Query(value = "select * from restaurant_table where restaurant_id = :rid",nativeQuery = true)
    List<RestaurantTable> getAllTableOfRes(@Param(value = "rid") Integer rid);

    @Modifying
    @Transactional
    @Query(value = "update restaurant_table set title = :titles, number_seat = :numbers where id = :tid",nativeQuery = true)
    void  updateTable(@Param(value = "titles")String titles,@Param(value = "numbers")Integer numbers,@Param(value = "tid")Integer tid);



}
