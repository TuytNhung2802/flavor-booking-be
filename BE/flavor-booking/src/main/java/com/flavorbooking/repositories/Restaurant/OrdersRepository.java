package com.flavorbooking.repositories.Restaurant;

import com.flavorbooking.models.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrdersRepository extends JpaRepository<Order,Integer>{

    //12 tháng từ đây về trước
//    @Query(nativeQuery = true,
//            value = "SELECT CONCAT(MONTH(date), '-', YEAR(date)) AS month_year, " +
//                    "COUNT(MONTH(date)) AS count " +
//                    "FROM orders " +
//                    "WHERE YEAR(date) BETWEEN YEAR(NOW()) - 1 AND YEAR(NOW()) " +
//                    "GROUP BY MONTH(date), YEAR(date) " +
//                    "ORDER BY YEAR(date), MONTH(date);")
//    List<Object[]> numberOfPeopleByMonth();

    // các tháng trong năm hiện tại
    @Query(nativeQuery = true,
            value = "SELECT COUNT(MONTH(date)) AS count " +
                    "FROM orders " +
                    "WHERE YEAR(date) = YEAR(NOW())  " +
                    "GROUP BY MONTH(date) " +
                    "ORDER BY MONTH(date);")
    List<Object[]> numberOfPeopleByMonth();

    @Query(nativeQuery = true,
            value = "select full_name,COUNT(id) as num\n" +
                    " from orders \n" +
                    " group by full_name \n" +
                    " order by num desc\n" +
                    " limit 5;")
    List<Object[]> customerOrderTheMost();


    @Query(nativeQuery = true, value = "SELECT time_from, time_to FROM orders\n" +
            " WHERE orders.restaurant_table_id = :tid\n" +
            " AND DATE(orders.date) = :date_order")
    List<Object[]> getOrderByDate(@Param("date_order") String date_order, @Param("tid") int tid);

    @Query(value = "SELECT o FROM Order o " +
            "WHERE (:id IS NULL OR o.id = :id) ",nativeQuery = true
    )
    Page<Order> getAllOrders(
            @Param("id") Integer id,
            Pageable pageable
    );

    @Query(nativeQuery = true, value = "select * from orders where restaurant_id=:rid and date=:date and product_status!=3")
    List<Order> getOrderCurrentDate(@Param("date") String date, @Param("rid") Integer rid);

    @Query(nativeQuery = true, value = "select * from orders where account_id=:uid order by (date) desc")
    List<Order> getOrderByUid(@Param("uid") Integer uid);

    @Query(nativeQuery = true, value = "select * from orders where restaurant_id=:rid order by (date) desc")
    List<Order> getOrderByRid(@Param("rid") Integer rid);

}
