package com.example.demo.network.service;

import com.example.chat.network.DAL.ChatRoomRepository;
import com.example.chat.network.models.ChatRoomModel;
import com.example.demo.network.DAL.UserRepository;
import com.example.demo.network.models.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class UserService implements UserDetailsService {

  private final UserRepository userRepository;
  private final ChatRoomRepository chatRoomRepository;

  @Autowired
  public UserService(UserRepository userRepository, ChatRoomRepository chatRoomRepository) {
    this.userRepository = userRepository;
    this.chatRoomRepository = chatRoomRepository;
  }

  public List<UserModel> getUsers() {
    return userRepository.findAll();
  }

  public void addNewUser(UserModel user) {
    boolean isEmailTaken = userRepository.existsByEmail(user.getEmail());
    boolean isUsernameTaken = userRepository.existsByUsername(user.getUsername());

    if (isUsernameTaken | isEmailTaken) {
      String message = isEmailTaken & isUsernameTaken
          ? "Username and Email already taken."
          : isUsernameTaken ? "Username already taken." : "Email already taken.";
      throw new IllegalStateException(message);
    }
    userRepository.save(user);
  }

  public void deleteUser(Long userId) {
    boolean exists = userRepository.existsById(userId);

    if (!exists) {
      throw new IllegalStateException("user with id " + userId + " doesn't exist");
    }
    userRepository.deleteById(userId);
  }

  @Transactional
  public void updateUser(UserModel user) {
    Long userId = user.getId();
    UserModel repositoryUser = userRepository.findById(userId)
        .orElseThrow(() -> new IllegalStateException("user with this id" + userId + " does not exist"));

    repositoryUser.update(user);
  }

//    @Transactional
//    public void updateCredentials(UserModel user) {
//        UserModel isUserExist = userRepository.findById(user.getId())
//                .orElseThrow(() -> new IllegalStateException("user with this id" + userId + " does not exist"));
//        boolean isUsernameAcceptable = username != null && username.length() > 0 && !Objects.equals(user.getUsername(), username);
//        boolean isEmailAcceptable = email != null && email.length() > 0 && !Objects.equals(user.getEmail(), email);
//
//        if (isUsernameAcceptable) {
//            user.setUsername(username);
//        }
//        if (isEmailAcceptable) {
//            user.setUsername(email);
//        }
//    }

  public UserModel getUserByName(String username) {
    return userRepository.findByUsername(username)
        .orElseThrow(() -> new IllegalStateException("user with this name" + username + " does not exist"));
  }

  @Override
  public UserDetails loadUserByUsername(String username) {
    UserModel user = userRepository.findByUsername(username)
        .orElseThrow(() -> new IllegalStateException("user with this name" + username + " does not exist"));

    return new User(user.getUsername(), user.getPassword(), new ArrayList<>());
  }

  public Optional<List<ChatRoomModel>> getChats(Long userId) {
    UserModel user = userRepository.findById(userId).orElseThrow(() -> new IllegalStateException("user with this id" + userId + " does not exist"));
    Set<UserModel> chatParticipants = new HashSet<>();
    chatParticipants.add(user);

    return chatRoomRepository.findByParticipantsEquals(user);
  }
}
