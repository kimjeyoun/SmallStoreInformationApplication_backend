package com.example.smallstore.Service;

import com.example.smallstore.Dto.Chat.ChatCreateRequest;
import com.example.smallstore.Entity.Chat;
import com.example.smallstore.Entity.User;
import com.example.smallstore.Repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatService {
    private final UserService userService;
    private final ChatRepository chatRepository;

    //채팅방 만들기
    public ResponseEntity createRoom(HttpServletRequest request, String toNickname) {
        User user = userService.findUserByToken(request);
        List<Chat> chatList = chatRepository.findByChatList(user.getNickname());
        // 만약 이미 생성된 채팅이라면
        if(!chatList.isEmpty()){
            Optional<Chat> chat = chatRepository.findByChatFromAndChatTo(user.getNickname(), toNickname);
            if(chat.isPresent()){
                return ResponseEntity.accepted().body(this.findById(request, chat.get().getChatId()));
            } else {
                Optional<Chat> chat2 = chatRepository.findByChatFromAndChatTo(toNickname, user.getNickname());
                return ResponseEntity.accepted().body(this.findById(request, chat2.get().getChatId()));
            }
        }
        // 처음 채팅한다면
        ChatCreateRequest chatCreateRequest = new ChatCreateRequest();
        chatCreateRequest.setChatId(UUID.randomUUID().toString());
        chatCreateRequest.setChatFrom(user.getNickname());
        chatCreateRequest.setChatTo(toNickname);
        chatRepository.save(chatCreateRequest.toEntity());
        return ResponseEntity.ok(chatCreateRequest);
    }

    // 모든 채팅방 가져오기
    public List<Chat> findAllChatList(HttpServletRequest request){
        User user = userService.findUserByToken(request);
        List<Chat> chatList = chatRepository.findByChatList(user.getNickname());
        return chatList;
    }

    //채팅방 하나 불러오기
    public Chat findById(HttpServletRequest request, String chatId) {
        User user = userService.findUserByToken(request);
        Chat chat = chatRepository.findByChatId(chatId).orElseThrow();
        if(!chat.getChatFrom().equals(user.getNickname())){
            chat.setChatTo(chat.getChatFrom());
            chat.setChatFrom(user.getNickname());
            return chat;
        }
        return chat;
    }
}
