package com.flavorbooking.repositories.Restaurant;

import com.flavorbooking.models.Dish;
import com.flavorbooking.request.CategoryRequest;
import com.flavorbooking.request.RestaurantRequest;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface DishRepository extends JpaRepository<Dish,Integer> {

    @Query(value = "select * from dish where restaurant_id =:rid",nativeQuery = true)
    List<Dish> getAllDishByRestaurant(@Param("rid")Integer rid);


    @Modifying
    @Transactional
    @Query(value = "update dish " +
            "set description = :descriptions," +
            "featured = :featured," +
            "image = :image," +
            "old_price = :oldPrice," +
            "price = :price," +
            "product_status = :productStatus," +
            "title = :title," +
            "updated = :updated," +
            "category_id = :cid " +
            "where id = :did ;",nativeQuery = true)
    void updateDish(@Param("did")Integer did, @Param("title")String title, @Param("image")String image, @Param("descriptions")String descriptions,
                    @Param("price")Double price,
                    @Param("oldPrice")Double oldPrice, @Param("productStatus")String productStatus, @Param("featured") Boolean featured,
                    @Param("updated")LocalDate updated,@Param("cid")Integer cid);


    List<Dish> findByRestaurant_Id(Integer id);

    @Query(value = "SELECT d FROM Dish d " +
            "WHERE (:id IS NULL OR d.id = :id) " +
            "AND (:description IS NULL OR d.description like %:description%) " +
            "AND (:price IS NULL OR d.price = :price) " +
            "AND (:price IS NULL OR d.featured = :featured) " +
            "AND (:categoryId IS NULL OR d.categories.id = :categoryId) " +
            "AND (:title IS NULL OR d.title like %:title%) ",nativeQuery = true
    )
    Page<Dish> getAllDish(
            @Param("id") Integer id,
            @Param("description") String description,
            @Param("price") Double price,
            @Param("featured") Boolean featured,
            @Param("categoryId") Integer categoryId,
            @Param("title") String title,
            Pageable pageable
    );

    @Query("SELECT DISTINCT d FROM Dish d " +
            "WHERE(:categoryIds IS NULL OR d.category.id IN :categoryIds) " +
            "AND (:min_price IS NULL OR d.price >= :min_price) " +
            "AND (:max_price IS NULL OR d.price <= :max_price) " +
            "AND (:restaurantId IS NULL OR d.restaurant.id IN :restaurantId) ")
    List<Dish> filterProduct(List<CategoryRequest> categoryIds, Double min_price, Double max_price, List<RestaurantRequest> restaurantId);
}
