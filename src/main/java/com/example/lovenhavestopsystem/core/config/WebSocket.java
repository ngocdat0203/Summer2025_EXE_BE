package com.example.lovenhavestopsystem.core.config;

import com.example.lovenhavestopsystem.socket.AuthChannelInterceptorAdapter;
import com.example.lovenhavestopsystem.socket.AuthHandshakeInterceptor;
//import com.example.lovenhavestopsystem.socket.ChatSocketHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocket implements WebSocketMessageBrokerConfigurer {

    private final AuthHandshakeInterceptor authHandshakeInterceptor;

    private final AuthChannelInterceptorAdapter authChannelInterceptorAdapter;

    public WebSocket(AuthHandshakeInterceptor authHandshakeInterceptor,
                     AuthChannelInterceptorAdapter authChannelInterceptorAdapter) {
        this.authHandshakeInterceptor = authHandshakeInterceptor;
        this.authChannelInterceptorAdapter = authChannelInterceptorAdapter;
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")// hoặc cấu hình cụ thể cho frontend
                .addInterceptors(authHandshakeInterceptor)
                .setAllowedOriginPatterns("*")
                .withSockJS(); // DÙNG SOCKJS ĐỂ HỖ TRỢ KẾT NỐI
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic", "/queue"); // Phải có /queue để gửi riêng
        registry.setApplicationDestinationPrefixes("/app");
        registry.setUserDestinationPrefix("/user"); // Phải có để @SendToUser hoặc convertAndSendToUser hoạt động đúng
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(authChannelInterceptorAdapter);
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
