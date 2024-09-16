package com.flavorbooking.repositories.admin;

import com.flavorbooking.models.*;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CountClientRepository extends JpaRepository<Account, Integer> {
    @Query(nativeQuery = true, value = "select * from Account")
    public List<Account> getAllTransaction();

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "select sum(role_name) as total_role_name from Account where role_name LIKE '%user'")
    public int sumAllCountClient();
}
