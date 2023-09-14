package com.example.smallstore.Entity;

import com.example.smallstore.Dto.Chat.ChatCreateRequest;
import com.example.smallstore.Dto.Shop.ShopRegisterRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import java.time.LocalDateTime;


@Data
@Entity
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "chat")
public class Chat {
    @ApiModelProperty(value = "chatId", example = "acc4e108-fe16-4093-b5ff-97ba1b3dfd94")
    @Id
    private String chatId;

    @ApiModelProperty(value = "chatFrom", example = "test")
    @Column(nullable = false)
    private String chatFrom;

    @ApiModelProperty(value = "chatTo", example = "shopTest")
    @Column(nullable = false)
    private String chatTo;

    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd/HH:mm:ss")
    private LocalDateTime createdAt;

}
