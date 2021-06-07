package com.example.chat.network.DAL;

import com.example.chat.network.models.ChatRoomModel;
import com.example.demo.network.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoomModel, Long> {
    Optional<ChatRoomModel> findByParticipantsIn(Set<UserModel> participant);
    Optional<List<ChatRoomModel>> findByParticipantsEquals(UserModel participant);
    Optional<ChatRoomModel> findByParticipantsEqualsAndParticipantsEquals(UserModel sender, UserModel sender2);
}
