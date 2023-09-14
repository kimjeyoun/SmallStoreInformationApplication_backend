package com.example.smallstore.Controller;

import com.example.smallstore.Dto.User.UserUpdateRequest;
import com.example.smallstore.Entity.Chat;
import com.example.smallstore.Service.ChatService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    // 채팅방 생성
    @ApiResponses( value ={
            @ApiResponse(code = 200, message = "채팅방 생성 완료"),
            @ApiResponse(code = 202, message = "채팅방 존재하여 존재하는 채팅방으로 보냄")
    })
    @ApiOperation(value = "채팅방 생성")
    @GetMapping("/room")
    @ResponseBody
    public ResponseEntity createRoom(HttpServletRequest request, @RequestParam String toNickname) {
        return chatService.createRoom(request, toNickname);
    }

    // 모든 채팅방 목록 반환
    @ApiResponses( value ={
            @ApiResponse(code = 200, message = "모든 채팅방 목록 반환 완료")
    })
    @ApiOperation(value = "모든 채팅방 목록 반환")
    @GetMapping("/rooms")
    @ResponseBody
    public List<Chat> room(HttpServletRequest request) {
        return chatService.findAllChatList(request);
    }

    // 특정 채팅방 조회
    @ApiResponses( value ={
            @ApiResponse(code = 200, message = "특정 채팅방 조회 완료", response = String.class ,responseContainer = "List")
    })
    @ApiOperation(value = "특정 채팅방 조회(1대1 톡방 들어갈 수 있음)")
    @GetMapping("/room/{chatId}")
    @ResponseBody
    public Chat roomInfo(HttpServletRequest request, @PathVariable String chatId) {
        return chatService.findById(request, chatId);
    }

}
