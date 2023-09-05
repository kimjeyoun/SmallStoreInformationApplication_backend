package com.example.smallstore.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class ChatConfig implements WebSocketMessageBrokerConfigurer {

    // Client에서 websocket연결할 때 사용할 API 경로를 설정해주는 메서드
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws/chat").setAllowedOriginPatterns("*").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 메시지 받을 때 관련 경로 설정
        // 주로 "/queue"는 1대1 메시징, "/topic"은 1대다 메시징일 때 주로 사용함.
        registry.enableSimpleBroker("/queue", "/topic");

        //메시지 보낼 때 관련 경로 설정
        registry.setApplicationDestinationPrefixes("/app");
    }
}
