package com.flavorbooking.repositories.Restaurant;

import com.flavorbooking.models.RestaurantWishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LikeRestaurantRepository extends JpaRepository<RestaurantWishlist, Integer> {
    @Query(nativeQuery = true, value = "select id from restaurant_wishlist where account_id=:uid and restaurant_id=:rid")
    public Integer checkLike(@Param("uid") Integer uid, @Param("rid") Integer rid);

    @Query(nativeQuery = true, value = "select *  from restaurant_wishlist where account_id=:uid")
    List<RestaurantWishlist> findAllbyUid(@Param("uid") Integer uid);
}
