package com.example.smallstore.Entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import java.time.LocalDateTime;

import com.example.smallstore.Entity.user;


@Data
@Entity
@Table(name = "chat")
public class chat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long chat_id;

    @Column(unique = true, nullable = false)
    private String chat_from;

    @Column(unique = true, nullable = false)
    private String chat_to;

    @DateTimeFormat(pattern = "yyyy-MM-dd/HH:mm:ss")
    private LocalDateTime created_at;

}
