package com.flavorbooking.repositories.Restaurant;

import com.flavorbooking.models.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RestaurantRepository  extends JpaRepository<Restaurant,Integer> {

    List<Restaurant> findByisActive(Boolean isActive);

    Restaurant findByAccount_Id(Integer id);



    @Query(value = "SELECT r FROM Restaurant r " +
            "WHERE (:id IS NULL OR r.id = :id) " +
            "AND (:title IS NULL OR r.title like %:title%) " +
            "AND (:description IS NULL OR r.description like %:description%) "
    )
    Page<Restaurant> getAllRestaurants(
            @Param("id") Integer id,
            @Param("title") String title,
            @Param("description") String description,
            Pageable pageable
    );

}
