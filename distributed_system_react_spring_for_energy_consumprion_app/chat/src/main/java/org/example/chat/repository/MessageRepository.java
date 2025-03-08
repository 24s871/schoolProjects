package org.example.chat.repository;

import org.example.chat.model.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<ChatMessage, Integer> {
    @Query("SELECT cm FROM ChatMessage cm WHERE (cm.senderId = :senderId1 AND cm.receiverId = :receiverId1) OR (cm.senderId = :senderId2 AND cm.receiverId = :receiverId2) ORDER BY cm.timestamp ASC")
    List<ChatMessage> findBySenderIdAndReceiverIdOrReceiverIdAndSenderIdOrderByTimestampAsc(
            @Param("senderId1") Integer senderId1,
            @Param("receiverId1") Integer receiverId1,
            @Param("senderId2") Integer senderId2,
            @Param("receiverId2") Integer receiverId2);

   //@Query("SELECT DISTINCT u.userId FROM User u WHERE u.userId != :currentUserId")
    //List<Integer> findAllChatUsers(@Param("currentUserId") Integer currentUserId);
}



