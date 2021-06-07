package com.example.chat.network.models;

//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
public class ChatNotificationModel {
    private Long id;
    private Long senderId;
    private String senderName;

    public ChatNotificationModel() {
    }

    public ChatNotificationModel(Long id, Long senderId, String senderName) {
        this.id = id;
        this.senderId = senderId;
        this.senderName = senderName;
    }

    public ChatNotificationModel(Long senderId, String senderName) {
        this.senderId = senderId;
        this.senderName = senderName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }
}
