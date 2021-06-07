package com.example.chat.network.conrollers;

import com.example.chat.network.models.ChatRoomModel;
import com.example.chat.network.service.ChatMessageService;
import com.example.chat.network.models.ChatMessageModel;
import com.example.chat.network.models.ChatNotificationModel;
import com.example.chat.network.service.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "chat")
public class ChatController {
	@Autowired
	private SimpMessagingTemplate messagingTemplate;
	@Autowired
	private ChatMessageService chatMessageService;
	@Autowired
	private ChatRoomService chatRoomService;

	@PutMapping(path = "sendMessage")
	public void processMessage(@RequestBody ChatMessageModel chatMessage) {
		ChatRoomModel chat = chatRoomService.getChat(chatMessage.getSenderId(), chatMessage.getRecipientId(), true).orElseThrow(() -> new IllegalStateException("chat with this id" + chatMessage.getId() + " does not exist"));

		chatMessage.setChatRoom(chat);
		ChatMessageModel saved = chatMessageService.save(chatMessage);

		messagingTemplate.convertAndSendToUser(
				Long.toString(chatMessage.getRecipientId()), "/queue/messages",
				new ChatNotificationModel(
						saved.getId(),
						saved.getSenderId(),
						saved.getSenderName()));
	}

	@GetMapping("/messages/{senderId}/{recipientId}/count")
	public ResponseEntity<Long> countNewMessages(
			@PathVariable Long senderId,
			@PathVariable Long recipientId) {

		return ResponseEntity
				.ok(chatMessageService.countNewMessages(senderId, recipientId));
	}

	@GetMapping("/messages/{senderId}/{recipientId}")
	public ResponseEntity<?> findChatMessages(@PathVariable Long senderId, @PathVariable Long recipientId) {
		return ResponseEntity
				.ok(chatMessageService.findChatMessages(senderId, recipientId));
	}

	@GetMapping("/messages/getOne/{id}")
	public ResponseEntity<?> findMessage(@PathVariable Long id) {
		return ResponseEntity
				.ok(chatMessageService.findById(id));
	}

	@GetMapping("/messages/getAll/{senderId}/{recipientId}")
	public List<ChatMessageModel> getAllMessages(@PathVariable Long senderId, @PathVariable Long recipientId) {
		ChatRoomModel chat = chatRoomService.getChat(senderId, recipientId, false).orElseThrow(() -> new IllegalStateException("chat with this id does not exist"));

		return chatMessageService.getAllUserMessages(chat);
	}
}
