package com.example.chat.network.DAL;

import com.example.chat.network.models.ChatMessageModel;
import com.example.chat.network.models.ChatRoomModel;
import com.example.chat.network.models.MessageStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessageModel, Long> {
    long countBySenderIdAndRecipientIdAndStatus(Long senderId, Long recipientId, MessageStatus status);

    List<ChatMessageModel> findAllByChatRoom(ChatRoomModel chatRoom);
    List<ChatMessageModel> findByChatRoom(ChatRoomModel chatRoom);
}
