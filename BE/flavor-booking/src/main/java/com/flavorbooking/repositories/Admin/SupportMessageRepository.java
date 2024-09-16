package com.flavorbooking.repositories.admin;

import com.flavorbooking.models.ChatMessage;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SupportMessageRepository extends JpaRepository<ChatMessage, Integer> {
    @Query(nativeQuery = true, value = "select * from ChatMessage")
    public List<ChatMessage> getAllTableOfRes();

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "select sum(id) as id from ChatMessage ")
    public int sumAllSupportMessage();
}
