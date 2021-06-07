package com.example.chat.network.models;
import com.example.demo.network.models.UserModel;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "chat_rooms")
public class ChatRoomModel implements Serializable {
    @SequenceGenerator(name = "chat_room_sequence", sequenceName = "chat_room_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "chat_room_sequence")
    @Id
    private Long id;
    private String chatId;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "chat_participants",
            joinColumns = @JoinColumn(name="chat_id", nullable=false),
            inverseJoinColumns = @JoinColumn(name = "users_id", nullable = false)
    )
    private Set<UserModel> participants = new HashSet<>();

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "chatRoom")
    private List<ChatMessageModel> messages;

    public ChatRoomModel() {
    }

    public ChatRoomModel(Long id, String chatId, Long senderId, Long recipientId) {
        this.id = id;
        this.chatId = chatId;
    }

    public ChatRoomModel(String chatId, Set<UserModel> participants) {
        this.chatId = chatId;
        this.participants = participants;
        this.messages = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public Set<UserModel> getParticipants() {
        return participants;
    }

    public void setParticipants(Set<UserModel> participants) {
        this.participants = participants;
    }

    public List<ChatMessageModel> getMessages() {
        return messages;
    }
}
