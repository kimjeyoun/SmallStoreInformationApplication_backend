package com.example.smallstore.Dto.Chat;

import com.example.smallstore.Entity.Chat;
import com.example.smallstore.Entity.EmailAuth;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data // get, set 둘 다 됨.
@RequiredArgsConstructor
public class ChatCreateRequest {
    @ApiModelProperty(example = "44b7f307-a944-4e14-8dd9-f81e759fd650")
    private String chatId;
    @ApiModelProperty(example = "test@naver.com")
    private String chatFrom;
    @ApiModelProperty(example = "test")
    private String chatTo;

    public Chat toEntity() {
        return Chat.builder()
                .chatId(chatId)
                .chatFrom(chatFrom)
                .chatTo(chatTo)
                .build();
    }
}
