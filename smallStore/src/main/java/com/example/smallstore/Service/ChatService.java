package com.example.smallstore.Service;

import com.example.smallstore.Dto.Chat.ChatCreateRequest;
import com.example.smallstore.Dto.User.Email.EmailSaveRequest;
import com.example.smallstore.Entity.Chat;
import com.example.smallstore.Entity.User;
import com.example.smallstore.Model.ChatRoom;
import com.example.smallstore.Repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatService {
    private Map<String, ChatRoom> chatRooms;
    private final UserService userService;
    private final ChatRepository chatRepository;

    @PostConstruct
    //의존관게 주입완료되면 실행되는 코드
    private void init() {
        chatRooms = new LinkedHashMap<>();
    }

    //채팅방 불러오기
    public List<ChatRoom> findAllRoom(HttpServletRequest request) {
        //User user = userService.findUserByToken(request);
        //채팅방 최근 생성 순으로 반환
        List<ChatRoom> result = new ArrayList<>(chatRooms.values());
        Collections.reverse(result);

        return result;
    }

    public List<String> findAllShopOwner(){
        List<String> result = new ArrayList<>();
        result.add("shopTest");
        result.add("shopTest2");
        return result;
    }

    //채팅방 생성
    public ChatRoom createRoom(HttpServletRequest request, @RequestParam String toNickname) {
        User user = userService.findUserByToken(request);
        System.out.println(toNickname+","+user.getNickname()+" 대화방");
        ChatRoom chatRoom = ChatRoom.create(toNickname);
        ChatCreateRequest chatCreateRequest = new ChatCreateRequest();
        chatCreateRequest.setChatId(chatRoom.getRoomId());
        chatCreateRequest.setChatFrom(user.getNickname());
        chatCreateRequest.setChatTo(toNickname);
        chatRepository.save(chatCreateRequest.toEntity());
        chatRooms.put(chatRoom.getRoomId(), chatRoom);
        return chatRoom;
    }
}
