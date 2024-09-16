package com.flavorbooking.repositories.Restaurant;

import com.flavorbooking.models.RestaurantReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RestaurantReviewRepository extends JpaRepository<RestaurantReview, Integer> {

    @Query(nativeQuery = true, value = "select id from restaurant_review where restaurant_id=:rid and account_id=:uid")
    Integer checkReview(@Param("rid") Integer rid, @Param("uid") Integer uid);

    @Query(nativeQuery = true, value = "select * from restaurant_review where restaurant_id=:rid")
    List<RestaurantReview> getReviewByRid(@Param("rid") Integer rid);
}
