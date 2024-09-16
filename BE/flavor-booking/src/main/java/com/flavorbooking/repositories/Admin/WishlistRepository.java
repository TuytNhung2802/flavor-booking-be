package com.flavorbooking.repositories.admin;

import com.flavorbooking.models.RestaurantWishlist;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WishlistRepository extends JpaRepository<RestaurantWishlist,Integer> {

@Query(value = "select * from restaurant_wishlist ",nativeQuery = true)
List<RestaurantWishlist> getAllTableOfRes();

@Modifying
@Transactional
@Query(value = "update tables set title = :titles, number_seat = :numbers ",nativeQuery = true)
void  updateTable(@Param(value = "titles")String titles,@Param(value = "numbers")Integer numbers);

@Modifying
@Transactional
@Query(value = "INSERT INTO tables (title, number_seat) VALUES (:titles, :numbers)",nativeQuery = true)
void  addTable(@Param(value = "titles")String titles,@Param(value = "numbers")Integer numbers);


}
