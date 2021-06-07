package com.example.chat.network.models;

import javax.persistence.*;
import java.util.Date;

//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
//@Document

@Entity
@Table(name = "chat_messages")
public class ChatMessageModel {
    @SequenceGenerator(name = "chat_message_sequence", sequenceName = "chat_message_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "chat_message_sequence")
    @Id
    private Long id;
    private Long senderId;
    private Long recipientId;
    private String senderName;
    private String recipientName;
    private String content;
    private Date timestamp;
    private MessageStatus status;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "chat_rooms_id", referencedColumnName = "chatId")
    private ChatRoomModel chatRoom;

    public ChatMessageModel() {
    }

    public ChatMessageModel(Long id, Long senderId, Long recipientId, String senderName, String recipientName, String content, Date timestamp, MessageStatus status) {
        this.id = id;
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.senderName = senderName;
        this.recipientName = recipientName;
        this.content = content;
        this.timestamp = timestamp;
        this.status = status;
    }

    public ChatMessageModel(ChatRoomModel chatRoom, Long senderId, Long recipientId, String senderName, String recipientName, String content, MessageStatus status) {
        this.chatRoom = chatRoom;
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.senderName = senderName;
        this.recipientName = recipientName;
        this.content = content;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public String getChatId() {
//        return chatId;
//    }
//
//    public void setChatId(String chatId) {
//        this.chatId = chatId;
//    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public Long getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(Long recipientId) {
        this.recipientId = recipientId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public MessageStatus getStatus() {
        return status;
    }

    public void setStatus(MessageStatus status) {
        this.status = status;
    }

    public ChatRoomModel getChatRoom() {
        return chatRoom;
    }

    public void setChatRoom(ChatRoomModel chatRoom) {
        this.chatRoom = chatRoom;
    }
}
