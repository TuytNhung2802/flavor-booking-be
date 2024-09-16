package com.flavorbooking.repositories.Restaurant;

import com.flavorbooking.models.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRepository extends JpaRepository<ChatMessage, Integer> {
    @Query(nativeQuery = true, value = "select * from chat_message as c "
            + "where (c.send_id=:uid or c.recipient_id=:uid) "
            + "and (c.send_id=:fid or c.recipient_id=:fid) order by c.date desc")
    List<ChatMessage> getChatByUid(@Param("uid") Integer uid, @Param("fid") Integer fid);
}
