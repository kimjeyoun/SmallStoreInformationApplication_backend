package com.example.smallstore.Service;

import com.example.smallstore.Dto.Chat.ChatCreateRequest;
import com.example.smallstore.Entity.Chat;
import com.example.smallstore.Entity.User;
import com.example.smallstore.Repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    public List<String> findAllChatList(HttpServletRequest request){
        User user = userService.findUserByToken(request);
        List<Chat> chatList = chatRepository.findByChatList(user.getNickname());
        List<String> list = new ArrayList<String>();
        if(chatRepository.findByChatList(user.getNickname()).isEmpty()){
            list.add("shopTest");
            list.add("shopTest2");
        } else {
            for(Chat str : chatList){
                if(str.getChatFrom().equals(user.getNickname())){
                    list.add(str.getChatTo());
                } else if (str.getChatTo().equals(user.getNickname())){
                    list.add(str.getChatFrom());
                }
            }
        }
        return list;
    }

    //채팅방 찾기
    public Chat findRoom(HttpServletRequest request, @RequestParam String toNickname) {
        User user = userService.findUserByToken(request);
        List<Chat> chatList = chatRepository.findByChatList(user.getNickname());
        // 처음 채팅한다면
        if(!chatRepository.existsChatByChatFromAndChatTo(user.getNickname(), toNickname)){
            if(chatList.isEmpty()){
                ChatCreateRequest chatCreateRequest = new ChatCreateRequest();
                chatCreateRequest.setChatId(UUID.randomUUID().toString());
                chatCreateRequest.setChatFrom(user.getNickname());
                chatCreateRequest.setChatTo(toNickname);
                chatRepository.save(chatCreateRequest.toEntity());
            } else {
                chatList.get(0).setChatTo(chatList.get(0).getChatFrom());
                chatList.get(0).setChatFrom(user.getNickname());
                Chat chat = chatList.get(0);
                return chat;
            }
        }
        // 만약 이미 생성된 채팅이라면
        Chat chat = chatRepository.findByChatFromAndChatTo(user.getNickname(), toNickname).orElseThrow();
        return chat;
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
