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
        System.out.println(chatList);
        // 처음 채팅한다면
        if(!chatRepository.existsChatByChatFromAndChatTo(user.getNickname(), toNickname)){
            System.out.println("처음");
            if(chatList.isEmpty()){
                System.out.println("first");
                ChatCreateRequest chatCreateRequest = new ChatCreateRequest();
                chatCreateRequest.setChatId(UUID.randomUUID().toString());
                chatCreateRequest.setChatFrom(user.getNickname());
                chatCreateRequest.setChatTo(toNickname);
                chatRepository.save(chatCreateRequest.toEntity());
            } else {
                System.out.println("second");
                chatList.get(0).setChatTo(chatList.get(0).getChatTo());
                chatList.get(0).setChatFrom(user.getNickname());
                System.out.println(chatList);
                Chat chat = chatList.get(0);
                return chat;
            }
        }
        System.out.println("third");
        // 만약 이미 생성된 채팅이라면
        Chat chat = chatRepository.findByChatFromAndChatTo(user.getNickname(), toNickname).orElseThrow();
        return chat;
    }

    //채팅방 하나 불러오기
    public Chat findById(String chatId) {
        return chatRepository.findByChatId(chatId).orElseThrow();
    }
}
