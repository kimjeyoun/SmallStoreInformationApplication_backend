package com.example.smallstore.Entity;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "event")
public class Event {
    @ApiModelProperty(value = "eventNum(auto)", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long eventNum;

    @ApiModelProperty(value = "eventName", example = "eventName")
    @Column(nullable = false)
    private String eventName;

    @ApiModelProperty(value = "eventImage", example = "image adress")
    @Column(nullable = false)
    private String eventImage;

    @ApiModelProperty(value = "이벤트 만료 시간", example = "2023-08-22/01:00")
    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd/HH:mm:ss")
    private LocalDateTime endDate;

}
