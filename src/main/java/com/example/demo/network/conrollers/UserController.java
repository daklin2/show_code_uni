package com.example.demo.network.conrollers;

import com.example.chat.network.models.ChatRoomModel;
import com.example.demo.network.models.UserModel;
import com.example.demo.network.service.UserService;
import com.fasterxml.jackson.core.JsonToken;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "user")
public class UserController {
	private final UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping(path = "getAll")
	public List<UserModel> getEntireUsers() {
		return userService.getUsers();
	}

	@GetMapping
	public UserModel getUserByToken() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		String username = userDetails.getUsername();

		return userService.getUserByName(username);
	}

	@PostMapping(path = "getUser")
	public UserModel getUserByName(@RequestBody UserModel username) {
		return userService.getUserByName(username.getUsername());
	}

	@PostMapping
	public void registerUser(@RequestBody UserModel user) {
		userService.addNewUser(user);
	}

	@DeleteMapping(path = "{userId}")
	public void deleteUser(@PathVariable("userId") Long userId) {
		userService.deleteUser(userId);
	}

	@PutMapping(path = "update")
	public void updateUser(@RequestBody UserModel user) {
		userService.updateUser(user);
	}

	@PostMapping(path = "getUserChats")
	public Optional<List<ChatRoomModel>> getUserChats(Long userId) {
		return userService.getChats(userId);
	}
}
