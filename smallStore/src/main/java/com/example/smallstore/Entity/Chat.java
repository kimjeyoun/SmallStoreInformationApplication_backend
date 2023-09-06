package com.example.smallstore.Entity;

import com.example.smallstore.Dto.Chat.ChatCreateRequest;
import com.example.smallstore.Dto.Shop.ShopRegisterRequest;
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
    @Id
    private String chatId;

    @Column(unique = true, nullable = false)
    private String chatFrom;

    @Column(unique = true, nullable = false)
    private String chatTo;

    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd/HH:mm:ss")
    private LocalDateTime createdAt;

}
