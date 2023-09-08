package com.example.smallstore.Controller;

import com.example.smallstore.Entity.Chat;
import com.example.smallstore.Service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {
    private final ChatService chatService;

    // 채팅 리스트 화면
    @GetMapping("/room")
    public String rooms(Model model) {
        return "/chat/room";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "/chat/login";
    }

    // 채팅방 입장 화면
    @GetMapping("/room/enter/{roomId}")
    public String roomDetail(Model model, @PathVariable String roomId) {
        model.addAttribute("roomId", roomId);
        return "/chat/roomdetail";
    }

    // 모든 채팅방 목록 반환
    @GetMapping("/rooms")
    @ResponseBody
    public List<String> room(HttpServletRequest request) {
        return chatService.findAllChatList(request);
    }
    // 채팅방 생성
    @PostMapping("/room")
    @ResponseBody
    public Chat createRoom(HttpServletRequest request, String toNickname) {
        return chatService.findRoom(request, toNickname);
    }

    // 특정 채팅방 조회
    @GetMapping("/room/{chatId}")
    @ResponseBody
    public Chat roomInfo(HttpServletRequest request, @PathVariable String chatId) {
        return chatService.findById(request, chatId);
    }

}
