package com.example.chat.network.service;

import com.example.chat.network.DAL.ChatMessageRepository;
import com.example.chat.network.exceptions.ResourceNotFoundException;
import com.example.chat.network.models.ChatMessageModel;
import com.example.chat.network.models.ChatRoomModel;
import com.example.chat.network.models.MessageStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatMessageService {
    @Autowired private ChatMessageRepository chatMessageRepository;
    @Autowired private ChatRoomService chatRoomService;

    public ChatMessageModel save(ChatMessageModel chatMessage) {
        chatMessage.setStatus(MessageStatus.RECEIVED);
        chatMessageRepository.save(chatMessage);
        return chatMessage;
    }

    public long countNewMessages(Long senderId, Long recipientId) {
        return chatMessageRepository.countBySenderIdAndRecipientIdAndStatus(
                senderId, recipientId, MessageStatus.RECEIVED);
    }

    public List<ChatMessageModel> findChatMessages(Long senderId, Long recipientId) {
        ChatRoomModel chatRoom = chatRoomService.getChat(senderId, recipientId, false)
            .orElseThrow(() -> new IllegalStateException("chat with this users id doesn't exist"));

        return chatMessageRepository.findByChatRoom(chatRoom);
    }

    public ChatMessageModel findById(Long id) {
        return chatMessageRepository
                .findById(id)
                .map(chatMessage -> {
                    chatMessage.setStatus(MessageStatus.DELIVERED);
                    return chatMessageRepository.save(chatMessage);
                })
                .orElseThrow(() ->
                        new ResourceNotFoundException("can't find message (" + id + ")"));
    }

    public List<ChatMessageModel> getAllUserMessages(ChatRoomModel chatRoom) {
        return chatMessageRepository.findAllByChatRoom(chatRoom);
    }

//    @Transactional
//    public void updateStatuses(String senderId, String recipientId, MessageStatus status) {
//        Query query = new Query(Criteria.where("senderId").is(senderId).and("recipientId").is(recipientId));
//        Update update = Update.update("status", status);
//        mongoOperations.updateMulti(query, update, ChatMessage.class);
//    }
}
