package com.example.chat.network.service;

import com.example.chat.network.DAL.ChatRoomRepository;
import com.example.chat.network.models.ChatRoomModel;
import com.example.demo.network.DAL.UserRepository;
import com.example.demo.network.models.UserModel;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class ChatRoomService {
	@Autowired
	private ChatRoomRepository chatRoomRepository;
	@Autowired
	private UserRepository userRepository;

	public Optional<ChatRoomModel> getChat(Long senderId, Long recipientId, boolean createIfNotExist) {
		UserModel sender = userRepository.findById(senderId).orElseThrow(() -> new IllegalStateException("user with this id" + senderId + " does not exist"));
		UserModel recipient = userRepository.findById(recipientId).orElseThrow(() -> new IllegalStateException("user with this id" + recipientId + " does not exist"));

		Set<UserModel> chatParticipants = new HashSet<>();
		chatParticipants.add(sender);
		chatParticipants.add(recipient);

		return chatRoomRepository
        .findByParticipantsIn(chatParticipants)
				.or(() -> {
					if (!createIfNotExist) {
						return Optional.empty();
					}
					var chatId = String.format("%s_%s", senderId, recipientId);

					ChatRoomModel chat = new ChatRoomModel(chatId, chatParticipants);
					chatRoomRepository.save(chat);

					return Optional.of(chat);
				});
	}
}
