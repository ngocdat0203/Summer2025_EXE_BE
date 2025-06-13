package com.example.lovenhavestopsystem.core.config;

import com.example.lovenhavestopsystem.socket.ChatSocketHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocket implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/chat")
                .setAllowedOrigins("*") // hoặc cấu hình cụ thể cho frontend
               .withSockJS(); // DÙNG SOCKJS
        ;
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/queue", "/topic"); // Broker cho gửi tin
        registry.setApplicationDestinationPrefixes("/app"); // prefix cho client gửi đến controller
        registry.setUserDestinationPrefix("/user"); // cho tin nhắn riêng tư
    }
}


/*
public class WebSocket implements WebSocketConfigurer {

    private final ChatSocketHandler chatSocketHandler;

    public WebSocketConfig(ChatSocketHandler chatSocketHandler) {
        this.chatSocketHandler = chatSocketHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(chatSocketHandler, "/ws/chat").setAllowedOrigins("*");
    }
}*/
