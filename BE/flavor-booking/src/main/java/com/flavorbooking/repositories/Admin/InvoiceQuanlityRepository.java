package com.flavorbooking.repositories.admin;

import com.flavorbooking.models.ChatMessage;
import com.flavorbooking.models.Order;
import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InvoiceQuanlityRepository extends JpaRepository<Order,Integer> {
    @Query(nativeQuery = true, value = "select * from Order")
    public List<Order> getAllTableOfRes();

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "select sum(id) as id from Order ")
    public int sumAllInvoiceQuanlity();
}
